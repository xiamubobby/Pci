package com.wonders.xlab.patient.application;

import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.realm.PatientInfoRealm;

/**
 * Created by hua on 15/12/17.
 * 用户授权信息
 */
public class AIManager {

    private final static Object object = new Object();

    private static AIManager aiManager;

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
        /**
         * 关闭友盟统计
         */
        MobclickAgent.onProfileSignOff();
        synchronized (object) {
            XApplication.realm.beginTransaction();
            XApplication.realm.clear(PatientInfoRealm.class);
            XApplication.realm.commitTransaction();
        }

    }

    private PatientInfoRealm getPatientInfo() {
        return XApplication.realm.where(PatientInfoRealm.class).findFirst();
    }

    public String getPatientId() {
        PatientInfoRealm patientInfo = getPatientInfo();
        if (null == patientInfo) {
            return "";
        }
        return patientInfo.getPatientId();
    }

    public String getPatientTel() {
        PatientInfoRealm patientInfo = getPatientInfo();
        if (null == patientInfo) {
            return "";
        }
        return patientInfo.getPatientPhoneNumber();
    }

    public String getPatientPortraitUrl() {
        PatientInfoRealm patientInfo = getPatientInfo();
        if (null == patientInfo || TextUtils.isEmpty(patientInfo.getPatientPortraitUrl())) {
            return Constant.DEFAULT_PORTRAIT;
        }
        return patientInfo.getPatientPortraitUrl();
    }

    public String getPatientName() {
        PatientInfoRealm patientInfo = getPatientInfo();
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
    }
}
