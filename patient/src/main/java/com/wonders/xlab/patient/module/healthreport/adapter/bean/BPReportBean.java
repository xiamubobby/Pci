package com.wonders.xlab.patient.module.healthreport.adapter.bean;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by hua on 16/3/20.
 */
public class BPReportBean extends RealmObject {
    /**
     * 血压，心率的状态值
     */
    @Ignore
    public final static int STATUS_LOW = -1;//偏低
    @Ignore
    public final static int STATUS_NORMAL = 0;//正常
    @Ignore
    public final static int STATUS_HIGH = 1;//偏高

    @Required
    private String patientId;
    private String id;
    private String highPressure = "";
    private int highPressureStatus;

    private String lowPressure = "";
    private int lowPressureStatus;

    private String heartRate = "";
    private int heartRateStatus;
    private long recordTimeInMill;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHighPressure() {
        return highPressure;
    }

    public void setHighPressure(String highPressure) {
        this.highPressure = highPressure;
    }

    public String getLowPressure() {
        return lowPressure;
    }

    public void setLowPressure(String lowPressure) {
        this.lowPressure = lowPressure;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public long getRecordTimeInMill() {
        return recordTimeInMill;
    }

    public void setRecordTimeInMill(long recordTimeInMill) {
        this.recordTimeInMill = recordTimeInMill;
    }

    public int getHighPressureStatus() {
        return highPressureStatus;
    }

    public void setHighPressureStatus(int highPressureStatus) {
        this.highPressureStatus = highPressureStatus;
    }

    public int getLowPressureStatus() {
        return lowPressureStatus;
    }

    public void setLowPressureStatus(int lowPressureStatus) {
        this.lowPressureStatus = lowPressureStatus;
    }

    public int getHeartRateStatus() {
        return heartRateStatus;
    }

    public void setHeartRateStatus(int heartRateStatus) {
        this.heartRateStatus = heartRateStatus;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
