package com.wonders.xlab.pci.module.record.monitor.bean;

/**
 * Created by sjy on 2015/12/22.
 */
public class BPNBean {
    /**
     * 舒张压
     */
    private String diastolicPressure;
    /**
     * 收缩压
     */
    private String  systolicPressure;
    private String heartRate;
    private Long recordTime;
    private long headerId;


    public String getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(String diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
    }

    public String getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(String systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }
}
