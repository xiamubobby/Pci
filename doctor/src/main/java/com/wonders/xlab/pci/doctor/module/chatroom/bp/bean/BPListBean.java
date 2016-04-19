package com.wonders.xlab.pci.doctor.module.chatroom.bp.bean;

/**
 * Created by hua on 16/2/22.
 */
public class BPListBean {
    private String headerTime;
    private String detailTime;
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

    public String getHeaderTime() {
        return headerTime;
    }

    public void setHeaderTime(String headerTime) {
        this.headerTime = headerTime;
    }

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

    public String getDetailTime() {
        return detailTime;
    }

    public void setDetailTime(String detailTime) {
        this.detailTime = detailTime;
    }

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }
}
