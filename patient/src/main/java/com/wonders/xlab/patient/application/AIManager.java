package com.wonders.xlab.patient.application;

import android.text.TextUtils;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.realm.PatientInfoRealm;

/**
 * Created by hua on 15/12/17.
 * 用户授权信息
 */
public class AIManager {

    private static AIManager aiManager;

    private static PatientInfoRealm patientInfo;

    static {
        patientInfo = XApplication.realm.where(PatientInfoRealm.class).findFirst();
    }

    private AIManager() {

    }

    public static AIManager getInstance() {
        if (aiManager == null) {
            synchronized (AIManager.class) {
                if (aiManager == null) {
                    aiManager = new AIManager();
                }
            }
        }
        return aiManager;
    }

    public boolean hasLogin() {
        return !TextUtils.isEmpty(getPatientId());
    }

    public void logout() {
        XApplication.realm.beginTransaction();
        XApplication.realm.clear(PatientInfoRealm.class);
        XApplication.realm.commitTransaction();
    }

    public String getPatientId() {
        if (null == patientInfo) {
            return "";
        }
        return patientInfo.getPatientId();
    }

    public String getPatientTel() {
        if (null == patientInfo) {
            return "";
        }
        return patientInfo.getPatientPhoneNumber();
    }

    public String getPatientPortraitUrl() {
        if (null == patientInfo) {
            return Constant.DEFAULT_PORTRAIT;
        }
        return patientInfo.getPatientPortraitUrl();
    }

    public String getPatientName() {
        if (null == patientInfo) {
            return "";
        }
        return patientInfo.getPatientName();
    }

    public void savePatientInfo(String patientId, String tel, String portraitUrl, String patientName) {
        XApplication.realm.beginTransaction();
        PatientInfoRealm patientInfoRealm = XApplication.realm.createObject(PatientInfoRealm.class);
        patientInfoRealm.setPatientId(patientId);
        patientInfoRealm.setPatientName(patientName);
        patientInfoRealm.setPatientPhoneNumber(tel);
        patientInfoRealm.setPatientPortraitUrl(portraitUrl);
        XApplication.realm.commitTransaction();

        patientInfo = XApplication.realm.where(PatientInfoRealm.class).findFirst();
    }
}
