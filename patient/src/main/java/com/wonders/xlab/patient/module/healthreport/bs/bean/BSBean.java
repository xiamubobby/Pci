package com.wonders.xlab.patient.module.healthreport.bs.bean;

import android.databinding.BaseObservable;

/**
 * Created by hua on 16/2/22.
 */
public class BSBean extends BaseObservable{
    private long headerId;
    private String headerTime;
    private String earlyMorningBS;
    private String breakfastBeforeBS;
    private String breakfastAfterBS;
    private String lunchBeforeBS;
    private String lunchAfterBS;
    private String dinnerBeforeBS;
    private String dinnerAfterBS;
    private String randomBS;
    private long recordTimeInMill;

    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }

    public String getHeaderTime() {
        return headerTime;
    }

    public void setHeaderTime(String headerTime) {
        this.headerTime = headerTime;
    }

    public String getEarlyMorningBS() {
        return earlyMorningBS;
    }

    public void setEarlyMorningBS(String earlyMorningBS) {
        this.earlyMorningBS = earlyMorningBS;
    }

    public String getBreakfastBeforeBS() {
        return breakfastBeforeBS;
    }

    public void setBreakfastBeforeBS(String breakfastBeforeBS) {
        this.breakfastBeforeBS = breakfastBeforeBS;
    }

    public String getBreakfastAfterBS() {
        return breakfastAfterBS;
    }

    public void setBreakfastAfterBS(String breakfastAfterBS) {
        this.breakfastAfterBS = breakfastAfterBS;
    }

    public String getLunchBeforeBS() {
        return lunchBeforeBS;
    }

    public void setLunchBeforeBS(String lunchBeforeBS) {
        this.lunchBeforeBS = lunchBeforeBS;
    }

    public String getLunchAfterBS() {
        return lunchAfterBS;
    }

    public void setLunchAfterBS(String lunchAfterBS) {
        this.lunchAfterBS = lunchAfterBS;
    }

    public String getDinnerBeforeBS() {
        return dinnerBeforeBS;
    }

    public void setDinnerBeforeBS(String dinnerBeforeBS) {
        this.dinnerBeforeBS = dinnerBeforeBS;
    }

    public String getDinnerAfterBS() {
        return dinnerAfterBS;
    }

    public void setDinnerAfterBS(String dinnerAfterBS) {
        this.dinnerAfterBS = dinnerAfterBS;
    }

    public String getRandomBS() {
        return randomBS;
    }

    public void setRandomBS(String randomBS) {
        this.randomBS = randomBS;
    }

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }
}
