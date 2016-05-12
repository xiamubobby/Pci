package com.wonders.xlab.patient.module.healthchart.bs.bean;

import android.databinding.BaseObservable;

/**
 * Created by hua on 16/2/22.
 */
public class BSBean extends BaseObservable {
    private long headerId;

    /**
     * 早餐前血糖
     */
    private float breakfastBeforeBS;
    private int breakfastBeforeStandard;

    /**
     * 早餐后血糖标准
     */
    private float breakfastAfterBS;
    private int breakfastAfterStandard;

    /**
     * 午餐前血糖
     */
    private float lunchBeforeBS;

    private int LunchBeforeStandard;

    /**
     * 午餐后血糖
     */
    private float lunchAfterBS;
    private int lunchAfterStandard;

    /**
     * 晚餐前血糖
     */
    private float dinnerBeforeBS;
    private int dinnerBeforeStandard;

    /**
     * 晚餐后血糖
     */
    private float dinnerAfterBS;
    private int dinnerAfterStandard;

    /**
     * 睡前
     */
    private float beforeSleepBS;
    private int beforeDawnStandard;

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

    public int getBreakfastBeforeStandard() {
        return breakfastBeforeStandard;
    }

    public void setBreakfastBeforeStandard(int breakfastBeforeStandard) {
        this.breakfastBeforeStandard = breakfastBeforeStandard;
    }

    public int getBreakfastAfterStandard() {
        return breakfastAfterStandard;
    }

    public void setBreakfastAfterStandard(int breakfastAfterStandard) {
        this.breakfastAfterStandard = breakfastAfterStandard;
    }

    public int getLunchBeforeStandard() {
        return LunchBeforeStandard;
    }

    public void setLunchBeforeStandard(int lunchBeforeStandard) {
        LunchBeforeStandard = lunchBeforeStandard;
    }

    public int getLunchAfterStandard() {
        return lunchAfterStandard;
    }

    public void setLunchAfterStandard(int lunchAfterStandard) {
        this.lunchAfterStandard = lunchAfterStandard;
    }

    public int getDinnerBeforeStandard() {
        return dinnerBeforeStandard;
    }

    public void setDinnerBeforeStandard(int dinnerBeforeStandard) {
        this.dinnerBeforeStandard = dinnerBeforeStandard;
    }

    public int getDinnerAfterStandard() {
        return dinnerAfterStandard;
    }

    public void setDinnerAfterStandard(int dinnerAfterStandard) {
        this.dinnerAfterStandard = dinnerAfterStandard;
    }

    public int getBeforeDawnStandard() {
        return beforeDawnStandard;
    }

    public void setBeforeDawnStandard(int beforeDawnStandard) {
        this.beforeDawnStandard = beforeDawnStandard;
    }
}
