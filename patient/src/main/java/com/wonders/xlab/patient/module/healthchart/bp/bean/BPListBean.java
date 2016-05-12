package com.wonders.xlab.patient.module.healthchart.bp.bean;

/**
 * Created by hua on 16/2/22.
 */
public class BPListBean {
    /**
     * 收缩压
     */
    private String systolic;
    /**
     * 舒张压
     */
    private String diastolic;
    private String heartRate;
    private long headerId;
    private long recordTimeInMill;

    /**
     * 心率标准
     */
    private int heartStandard;
    /**
     * 收缩压标准
     */
    private int systolicStandard;
    /**
     * 舒张压标准
     */
    private int diastolicStandard;

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }

    public int getHeartStandard() {
        return heartStandard;
    }

    public void setHeartStandard(int heartStandard) {
        this.heartStandard = heartStandard;
    }

    public int getSystolicStandard() {
        return systolicStandard;
    }

    public void setSystolicStandard(int systolicStandard) {
        this.systolicStandard = systolicStandard;
    }

    public int getDiastolicStandard() {
        return diastolicStandard;
    }

    public void setDiastolicStandard(int diastolicStandard) {
        this.diastolicStandard = diastolicStandard;
    }
}
