package com.wonders.xlab.pci.module.task.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.wonders.xlab.pci.module.task.WeekViewFragment;

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
    private long mToday;

    public Long getTime(int position) {
        if (times != null && times.size() > position) {
            return times.get(position);
        } else {
            return -1l;
        }
    }

    public WeekViewVPAdapter(FragmentManager fm, long today) {
        super(fm);
        mToday = today;
        long first = calculateFirstDayOfWeekInMonth(today);
        for (int i = -INITIAL_POSITION; i <= 0; i++) {
            mCalendar.setTimeInMillis(first);
            mCalendar.add(Calendar.DATE, i * 7);
            times.add(mCalendar.getTimeInMillis());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return WeekViewFragment.newInstance(times.get(position), mToday);
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
