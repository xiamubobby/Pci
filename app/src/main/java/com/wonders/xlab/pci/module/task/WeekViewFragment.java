package com.wonders.xlab.pci.module.task;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.task.mvn.entity.DailyTaskEntity.RetValuesEntity.UserActivityDtosEntity;
import com.wonders.xlab.pci.module.task.rxbus.WeekViewClickBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekViewFragment extends BaseFragment {

    private long mFirstDayOfWeekInMonth;
    private long mToday;
    private int today;
    private List<UserActivityDtosEntity> mRemindList;
    private LayoutInflater mInflater;

    @Bind(R.id.ll_fragment_date)
    LinearLayout mLlFragmentDate;

    private Calendar calendar = Calendar.getInstance();

    public void setRemindList(List<UserActivityDtosEntity> remindList) {
        if (mRemindList == null) {
            mRemindList = new ArrayList<>();
        } else {
            mRemindList.clear();
        }
        mRemindList.addAll(remindList);
    }

    public static WeekViewFragment newInstance(long firstDayOfWeekInMonth, Long today, List<UserActivityDtosEntity> remindList) {
        // Required empty public constructor
        WeekViewFragment weekViewFragment = new WeekViewFragment();
        Bundle data = new Bundle();
        data.putLong("firstDayOfWeekInMonth", firstDayOfWeekInMonth);
        data.putLong("today", today);
        data.putParcelableArrayList("remindList", (ArrayList<? extends Parcelable>) remindList);
        weekViewFragment.setArguments(data);
        return weekViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data == null) {
            throw new IllegalArgumentException("you must set the time argument");
        }
        mFirstDayOfWeekInMonth = data.getLong("firstDayOfWeekInMonth");
        mToday = data.getLong("today", calendar.getTimeInMillis());
        mRemindList = data.getParcelableArrayList("remindList");
        today = Integer.parseInt(DateUtil.format(mToday, "dd"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date, container, false);
        ButterKnife.bind(this, view);

        setupDate(mFirstDayOfWeekInMonth);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInflater = LayoutInflater.from(context);
    }

    private void setupDate(long time) {
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeInMillis(time);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        int dayOfWeek = calendar.get(Calendar.DAY_OF_MONTH);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < 7; i++) {
            String showDate;

            View itemView = inflater.inflate(R.layout.item_daily_task_date, null, false);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) itemView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            } else {
                layoutParams.weight = 1;
            }

            TextView mTvDate = (TextView) itemView.findViewById(R.id.tv_item_daily_task_date);
            mTvDate.setTag(calendar.getTimeInMillis());
            itemView.setLayoutParams(layoutParams);

            if (dayOfWeek == 1) {
                showDate = (calendar.get(Calendar.MONTH) + 1) + "\n月";
                mTvDate.setTextSize(8);
            } else {
                showDate = String.valueOf(dayOfWeek);
            }

            mTvDate.setText(showDate);

            final int finalDayOfWeek = dayOfWeek;
            mTvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (UserActivityDtosEntity entity : mRemindList) {
                        if (DateUtil.isTheSameDay(entity.getCurrentDay(), (Long) v.getTag())) {
                            PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                            for (String name : entity.getNames()) {
                                popupMenu.getMenu().add(name);
                            }
                            popupMenu.show();
                            break;
                        }
                    }
                    if (finalDayOfWeek <= today) {
                        RxBus.getInstance().send(new WeekViewClickBus((Long) v.getTag(), finalDayOfWeek));
                    }
                }
            });


            if (mToday == calendar.getTimeInMillis()) {
                mTvDate.setSelected(true);
            } else {
                mTvDate.setSelected(false);
            }
            mLlFragmentDate.addView(itemView);

            calendar.add(Calendar.DATE, 1);
            dayOfWeek = calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
