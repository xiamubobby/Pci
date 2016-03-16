package com.wonders.xlab.patient.assist.deviceconnection.aamodel;

/**
 * Created by hua on 15/11/9.
 */
public class BPAAModel{

    /**
     * 收缩压，高压
     */
    private int systolicPressure;
    private int diastolicPressure;
    private int heartRate;
    private int averagePressure;
    private long date;

    public BPAAModel(long date, int systolicPressure, int diastolicPressure, int heartRate, int averagePressure) {
        this.date = date;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;
        this.averagePressure = averagePressure;
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
