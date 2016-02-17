package com.wonders.xlab.pci.module.task.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import im.hua.utils.DateUtil;
import com.wonders.xlab.pci.module.task.WeekViewFragment;
import com.wonders.xlab.pci.module.base.mvn.entity.task.DailyTaskEntity.RetValuesEntity.UserActivityDtosEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hua on 15/12/15.
 */
public class WeekViewVPAdapter extends FragmentPagerAdapter {
    public final static int INITIAL_POSITION = 100;
    private Calendar mCalendar = Calendar.getInstance();
    private List<Long> times = new ArrayList<>();
    private List<UserActivityDtosEntity> mRemindList;
    private long mToday;

    public WeekViewVPAdapter(FragmentManager fm, long today, List<UserActivityDtosEntity> remindList) {
        super(fm);
        mToday = today;
        this.mRemindList = remindList;
        long first = DateUtil.calculateFirstDayOfWeek(today);
        for (int i = -INITIAL_POSITION; i <= 2; i++) {
            mCalendar.setTimeInMillis(first);
            mCalendar.add(Calendar.DATE, i * 7);
            times.add(mCalendar.getTimeInMillis());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return WeekViewFragment.newInstance(times.get(position), mToday, mRemindList);
    }

    @Override
    public int getCount() {
        return times.size();
    }

}
