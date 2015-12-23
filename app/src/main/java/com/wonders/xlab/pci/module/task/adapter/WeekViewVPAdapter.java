package com.wonders.xlab.pci.module.task.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.wonders.xlab.pci.module.task.WeekViewFragment;
import com.wonders.xlab.pci.module.task.mvn.entity.DailyTaskEntity.RetValuesEntity.UserActivityDtosEntity;

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

    public Long getTime(int position) {
        if (times != null && times.size() > position) {
            return times.get(position);
        } else {
            return -1l;
        }
    }

    /*public void setRemindList(List<UserActivityDtosEntity> remindList) {
        if (mRemindList == null) {
            mRemindList = new ArrayList<>();
        } else {
            mRemindList.clear();
        }
        mRemindList.addAll(remindList);
        for (int i = 0; i < getCount(); i++) {
            ((WeekViewFragment) getItem(i)).setRemindList(mRemindList);
        }
        notifyDataSetChanged();
    }*/

    public WeekViewVPAdapter(FragmentManager fm, long today, List<UserActivityDtosEntity> remindList) {
        super(fm);
        mToday = today;
        this.mRemindList = remindList;
        long first = calculateFirstDayOfWeekInMonth(today);
        for (int i = -INITIAL_POSITION; i <= 0; i++) {
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

    private long calculateFirstDayOfWeekInMonth(long time) {
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.setTimeInMillis(time);
        while (mCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            mCalendar.add(Calendar.DATE, -1);
        }
        return mCalendar.getTimeInMillis();
    }
}
