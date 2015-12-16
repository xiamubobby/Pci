package com.wonders.xlab.pci.module.task.bean;

/**
 * Created by hua on 15/12/16.
 */
public class BloodPressureBean {
    private double value;
    private long time;

    public BloodPressureBean(double value, long time) {
        this.value = value;
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
