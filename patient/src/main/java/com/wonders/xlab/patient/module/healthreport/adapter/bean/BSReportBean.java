package com.wonders.xlab.patient.module.healthreport.adapter.bean;

/**
 * Created by hua on 16/3/20.
 */
public class BSReportBean {
    /**
     * 血糖的状态值
     */
    public final static int STATUS_LOW = -1;//偏低
    public final static int STATUS_NORMAL = 0;//正常
    public final static int STATUS_HIGH = 1;//偏高

    private String id;
    private String bloodSugar;
    private int bloodSugarStatus;
    private String measurePeriod;
    private long measureTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public int getBloodSugarStatus() {
        return bloodSugarStatus;
    }

    public void setBloodSugarStatus(int bloodSugarStatus) {
        this.bloodSugarStatus = bloodSugarStatus;
    }

    public long getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(long measureTime) {
        this.measureTime = measureTime;
    }

    public String getMeasurePeriod() {
        return measurePeriod;
    }

    public void setMeasurePeriod(String measurePeriod) {
        this.measurePeriod = measurePeriod;
    }
}
