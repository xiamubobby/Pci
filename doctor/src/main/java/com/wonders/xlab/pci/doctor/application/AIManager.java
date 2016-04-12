package com.wonders.xlab.pci.doctor.application;

import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.realm.DoctorInfoRealm;
import com.wonders.xlab.pci.doctor.realm.UnReadMessageRealm;

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
        return !TextUtils.isEmpty(getDoctorId());
    }

    public void logout() {
        /**
         * 关闭友盟统计
         */
        MobclickAgent.onProfileSignOff();
        synchronized (object) {
            XApplication.realm.beginTransaction();
            XApplication.realm.clear(DoctorInfoRealm.class);
            XApplication.realm.clear(UnReadMessageRealm.class);
            XApplication.realm.commitTransaction();
        }

    }

    private DoctorInfoRealm getDoctorInfo() {
        try {
            return XApplication.realm.where(DoctorInfoRealm.class).findFirst();
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public String getDoctorId() {
        DoctorInfoRealm doctorInfo = getDoctorInfo();
        if (null == doctorInfo) {
            return "";
        }
        return doctorInfo.getDoctorId();
    }

    public String getDoctorTel() {
        DoctorInfoRealm doctorInfo = getDoctorInfo();
        if (null == doctorInfo) {
            return "";
        }
        return doctorInfo.getDoctorTel();
    }

    public String getDoctorPortraitUrl() {
        DoctorInfoRealm doctorInfo = getDoctorInfo();
        if (null == doctorInfo || TextUtils.isEmpty(doctorInfo.getDoctorAvatarUrl())) {
            return Constant.DEFAULT_PORTRAIT;
        }
        return doctorInfo.getDoctorAvatarUrl();
    }

    public String getDoctorName() {
        DoctorInfoRealm doctorInfo = getDoctorInfo();
        if (null == doctorInfo) {
            return "";
        }
        return doctorInfo.getDoctorName();
    }

    public void saveDoctorInfo(String patientId, String tel, String portraitUrl, String patientName) {
        XApplication.realm.beginTransaction();
        DoctorInfoRealm DoctorInfoRealm = XApplication.realm.createObject(DoctorInfoRealm.class);
        DoctorInfoRealm.setDoctorId(patientId);
        DoctorInfoRealm.setDoctorName(patientName);
        DoctorInfoRealm.setDoctorTel(tel);
        DoctorInfoRealm.setDoctorAvatarUrl(portraitUrl);
        XApplication.realm.commitTransaction();
    }
}
