package com.wonders.xlab.pci.assist.connection.entity;

import com.wonders.xlab.pci.assist.connection.aamodel.BPAAModel;

/**
 * Created by hua on 16/1/6.
 */
public class BPEntity {
    /**
     * 收缩压，高压
     */
    private int systolicPressure;
    private int diastolicPressure;
    private int heartRate;
    private int averagePressure;
    private long date;

    public void setBPModel(BPAAModel BPAAModel) {
        setHeartRate(BPAAModel.getHeartRate());
        setSystolicPressure(BPAAModel.getSystolicPressure());
        setDiastolicPressure(BPAAModel.getDiastolicPressure());
        BPAAModel.setDate(BPAAModel.getDate());
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getAveragePressure() {
        return averagePressure;
    }

    public void setAveragePressure(int averagePressure) {
        this.averagePressure = averagePressure;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
