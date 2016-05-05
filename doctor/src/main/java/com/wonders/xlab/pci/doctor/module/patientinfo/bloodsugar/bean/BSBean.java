package com.wonders.xlab.pci.doctor.module.patientinfo.bloodsugar.bean;

/**
 * Created by hua on 16/2/22.
 */
public class BSBean {
    private long headerId;
    private float breakfastBeforeBS;
    private float breakfastAfterBS;
    private float lunchBeforeBS;
    private float lunchAfterBS;
    private float dinnerBeforeBS;
    private float dinnerAfterBS;
    private float beforeSleepBS;
    private long recordTimeInMill;

    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }

    public float getBreakfastBeforeBS() {
        return breakfastBeforeBS;
    }

    public void setBreakfastBeforeBS(float breakfastBeforeBS) {
        this.breakfastBeforeBS = breakfastBeforeBS;
    }

    public float getBreakfastAfterBS() {
        return breakfastAfterBS;
    }

    public void setBreakfastAfterBS(float breakfastAfterBS) {
        this.breakfastAfterBS = breakfastAfterBS;
    }

    public float getLunchBeforeBS() {
        return lunchBeforeBS;
    }

    public void setLunchBeforeBS(float lunchBeforeBS) {
        this.lunchBeforeBS = lunchBeforeBS;
    }

    public float getLunchAfterBS() {
        return lunchAfterBS;
    }

    public void setLunchAfterBS(float lunchAfterBS) {
        this.lunchAfterBS = lunchAfterBS;
    }

    public float getDinnerBeforeBS() {
        return dinnerBeforeBS;
    }

    public void setDinnerBeforeBS(float dinnerBeforeBS) {
        this.dinnerBeforeBS = dinnerBeforeBS;
    }

    public float getDinnerAfterBS() {
        return dinnerAfterBS;
    }

    public void setDinnerAfterBS(float dinnerAfterBS) {
        this.dinnerAfterBS = dinnerAfterBS;
    }

    public float getBeforeSleepBS() {
        return beforeSleepBS;
    }

    public void setBeforeSleepBS(float beforeSleepBS) {
        this.beforeSleepBS = beforeSleepBS;
    }

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }
}