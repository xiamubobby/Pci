package com.wonders.xlab.patient.module.healthreport.adapter.bean;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by hua on 16/3/20.
 */
public class BSReportBean extends RealmObject{
    /**
     * 血糖的状态值
     */
    @Ignore
    public final static int STATUS_LOW = -1;//偏低
    @Ignore
    public final static int STATUS_NORMAL = 0;//正常
    @Ignore
    public final static int STATUS_HIGH = 1;//偏高

    private String patientId;
    private String id;
    private String bloodSugar;
    private int bloodSugarStatus;
    private String measurePeriod;
    private long recordTimeInMill;

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

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }

    public String getMeasurePeriod() {
        return measurePeriod;
    }

    public void setMeasurePeriod(String measurePeriod) {
        this.measurePeriod = measurePeriod;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
