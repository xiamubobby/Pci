package com.wonders.xlab.pci.assist.connection.entity;

import com.wonders.xlab.pci.assist.connection.aamodel.BSAAModel;

/**
 * Created by hua on 15/11/9.
 */
public class BSEntity {

    private double bloodSugarValue;
    private long date;
    private int timeIndex;

    public BSEntity() {
    }

    public BSEntity(double bloodSugarValue, long date, int timeIndex) {
        this.bloodSugarValue = bloodSugarValue;
        this.date = date;
        this.timeIndex = timeIndex;
    }

    public void setBSModel(BSAAModel bsAAModel) {
        setBloodSugarValue(bsAAModel.getBloodSugar());
        setDate(bsAAModel.getDate());
        setTimeIndex(bsAAModel.getTimeIndex());
    }

    public double getBloodSugarValue() {
        return bloodSugarValue;
    }

    public void setBloodSugarValue(double bloodSugarValue) {
        this.bloodSugarValue = bloodSugarValue;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getTimeIndex() {
        return timeIndex;
    }

    public void setTimeIndex(int timeIndex) {
        this.timeIndex = timeIndex;
    }
}
