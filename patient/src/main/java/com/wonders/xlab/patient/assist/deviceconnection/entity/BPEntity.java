package com.wonders.xlab.patient.assist.deviceconnection.entity;


import com.wonders.xlab.patient.assist.deviceconnection.aamodel.BPAAModel;

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

    public void setBPModel(BPAAModel bpaaModel) {
        setDate(bpaaModel.getDate());
        setHeartRate(bpaaModel.getHeartRate());
        setSystolicPressure(bpaaModel.getSystolicPressure());
        setDiastolicPressure(bpaaModel.getDiastolicPressure());
        bpaaModel.setDate(bpaaModel.getDate());
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
