package com.wonders.xlab.pci.module.task;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends BaseFragment {

    private long time;

    @Bind(R.id.ll_fragment_date)
    LinearLayout mLlFragmentDate;

    public DateFragment newInstance(long time) {
        // Required empty public constructor
        DateFragment dateFragment = new DateFragment();
        Bundle data = new Bundle();
        data.putLong("time", time);
        dateFragment.setArguments(data);
        return dateFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data == null) {
            throw new IllegalArgumentException("you must set the time argument");
        }
        time = data.getLong("time");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date, container, false);
        ButterKnife.bind(this, view);

        setupDate(time);
        return view;
    }

    private void setupDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeInMillis(time);
        int today = calendar.get(Calendar.DAY_OF_MONTH);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        int dayOfWeek = calendar.get(Calendar.DAY_OF_MONTH);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < 7; i++) {
            View itemView = inflater.inflate(R.layout.item_daily_task_date, null, false);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) itemView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            } else {
                layoutParams.weight = 1;
            }
            TextView mTvDate = (TextView) itemView.findViewById(R.id.tv_item_daily_task_date);
            mTvDate.setText(String.valueOf(dayOfWeek));
            itemView.setLayoutParams(layoutParams);
            if (today == dayOfWeek) {
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
