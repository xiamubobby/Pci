package com.wonders.xlab.pci.module.task.otto;

/**
 * Created by hua on 15/12/16.
 */
public class WeekViewClickOtto {
    private long time;
    private int dayOfMonth;

    public WeekViewClickOtto(long time, int dayOfMonth) {
        this.time = time;
        this.dayOfMonth = dayOfMonth;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
