package com.wonders.xlab.pci.module.task.bean;

/**
 * Created by hua on 15/12/16.
 */
public class BloodPressureBean {
    /**
     * 收缩压
     */
    private float systolicPressure;
    /**
     * 舒张压
     */
    private float diastolicPressure;
    /**
     * 血压
     */
    private float bloodPressure;
    /**
     * 测量时间
     */
    private long time;

    public BloodPressureBean(float systolicPressure, float diastolicPressure, float bloodPressure, long time) {
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.bloodPressure = bloodPressure;
        this.time = time;
    }

    public float getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(float bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(float systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public float getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(float diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }
}
