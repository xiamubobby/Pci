package com.wonders.xlab.pci.module.record.monitor.bean;

/**
 * Created by sjy on 2015/12/22.
 */
public class BpBean{
    private String diastolicPressure;
    private String  systolicPressure;
    private String userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BpBean{" +
                "diastolicPressure='" + diastolicPressure + '\'' +
                ", systolicPressure='" + systolicPressure + '\'' +
                ", userId='" + userId + '\'' +
                ", heartRate='" + heartRate + '\'' +
                ", recordTime='" + recordTime + '\'' +
                '}';
    }

    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }
}
