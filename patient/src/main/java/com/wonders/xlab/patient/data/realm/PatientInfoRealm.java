package com.wonders.xlab.patient.data.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/3/28.
 */
public class PatientInfoRealm extends RealmObject {
    private String patientId;
    private String patientName;
    private String patientSex;
    private String patientAge;
    private String patientPortraitUrl;
    private String patientPhoneNumber;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientPortraitUrl() {
        return patientPortraitUrl;
    }

    public void setPatientPortraitUrl(String patientPortraitUrl) {
        this.patientPortraitUrl = patientPortraitUrl;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }
}
