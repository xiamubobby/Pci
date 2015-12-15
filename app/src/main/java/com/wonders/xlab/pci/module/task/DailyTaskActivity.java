package com.wonders.xlab.pci.module.task;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DailyTaskActivity extends AppbarActivity {

    @Bind(R.id.ll_daily_task_date)
    LinearLayout mLlDailyTaskDate;
    @Bind(R.id.tv_daily_task_title)
    TextView mTvDailyTaskTitle;
    @Bind(R.id.tl_daily_task_time_period_medicine)
    TabLayout mTlDailyTaskTimePeriodMedicine;
    @Bind(R.id.fl_daily_task_medicine)
    FlowLayout mFlDailyTaskMedicine;
    @Bind(R.id.fl_daily_task_symptom)
    FlowLayout mFlDailyTaskSymptom;
    @Bind(R.id.tl_daily_task_time_period_bp)
    TabLayout mTlDailyTaskTimePeriodBp;
    @Bind(R.id.et_daily_task_cigarette)
    EditText mEtDailyTaskCigarette;
    @Bind(R.id.tl_daily_task_time_period_bs)
    TabLayout mTlDailyTaskTimePeriodBs;
    @Bind(R.id.et_daily_task_wine)
    EditText mEtDailyTaskWine;

    @Override
    public int getContentLayout() {
        return R.layout.activity_daily_task;
    }

    @Override
    public String getToolbarTitle() {
        return "每日健康任务";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setupDate(Calendar.getInstance().getTimeInMillis());
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

        LayoutInflater inflater = LayoutInflater.from(this);
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
            mLlDailyTaskDate.addView(itemView);

            calendar.add(Calendar.DATE,1);
            dayOfWeek = calendar.get(Calendar.DAY_OF_MONTH);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
