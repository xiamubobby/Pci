package com.wonders.xlab.patient.application;

import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.data.realm.MedicationUsagesRealm;
import com.wonders.xlab.patient.data.realm.PatientInfoRealm;
import com.wonders.xlab.patient.data.realm.UnReadMessageRealm;
import com.wonders.xlab.patient.util.RealmUtil;

/**
 * Created by hua on 15/12/17.
 * 用户授权信息
 */
public class AIManager {

    private final static Object object = new Object();

    private static AIManager aiManager;

//    private AIManager() {
//
//    }

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
            RealmUtil.deleteRealm(PatientInfoRealm.class);
            RealmUtil.deleteRealm(UnReadMessageRealm.class);
//            RealmUtil.deleteRealm(MedicineRemindRealm.class);
//            RealmUtil.deleteRealm(MedicationUsagesRealm.class);
        }

    }

    private PatientInfoRealm getPatientInfo() {
        try {
            return XApplication.realm.where(PatientInfoRealm.class).findFirst();
        } catch (IllegalStateException e) {
            return null;
        }
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

    public String getPatientSex() {
        PatientInfoRealm patientInfo = getPatientInfo();
        if (null == patientInfo) {
            return "";
        }
        return patientInfo.getPatientSex();
    }

    public String getPatientAge() {
        PatientInfoRealm patientInfo = getPatientInfo();
        if (null == patientInfo) {
            return "";
        }
        return patientInfo.getPatientAge();
    }

    public void savePatientInfo(String patientId, String tel, String portraitUrl, String patientName, String sex, String age) {
        XApplication.realm.beginTransaction();
        PatientInfoRealm patientInfoRealm = XApplication.realm.createObject(PatientInfoRealm.class);
        patientInfoRealm.setPatientId(patientId);
        patientInfoRealm.setPatientName(patientName);
        patientInfoRealm.setPatientSex(sex);
        patientInfoRealm.setPatientAge(age);
        patientInfoRealm.setPatientPhoneNumber(tel);
        patientInfoRealm.setPatientPortraitUrl(portraitUrl);
        XApplication.realm.commitTransaction();
    }

    public void modifyPatientInfo(String patientId, String tel, String portraitUrl, String patientName, String sex, String age) {
        XApplication.realm.beginTransaction();
        PatientInfoRealm patientInfoRealm = getPatientInfo();
        if (patientInfoRealm != null) {
            if (patientId != null) {
                patientInfoRealm.setPatientId(patientId);
            }
            if (patientName != null) {
                patientInfoRealm.setPatientName(patientName);
            }
            if (sex != null) {
                patientInfoRealm.setPatientSex(sex);
            }
            if (age != null) {
                patientInfoRealm.setPatientAge(age);
            }
            if (tel != null) {
                patientInfoRealm.setPatientPhoneNumber(tel);
            }
            if (portraitUrl != null) {
                patientInfoRealm.setPatientPortraitUrl(portraitUrl);
            }
        }
        XApplication.realm.commitTransaction();
    }
}
