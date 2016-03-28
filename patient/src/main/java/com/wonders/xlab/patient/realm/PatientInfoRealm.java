package com.wonders.xlab.patient.realm;

import io.realm.RealmObject;

/**
 * Created by hua on 16/3/28.
 */
public class PatientInfoRealm extends RealmObject {
    private String patientId;
    private String patientName;
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
