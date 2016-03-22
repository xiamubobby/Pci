package com.wonders.xlab.patient.application;

import android.content.Context;
import android.text.TextUtils;

import com.wonders.xlab.common.manager.SPManager;

/**
 * Created by hua on 15/12/17.
 * 用户授权信息
 */
public class AIManager {
    /**
     * Preference key
     */
    public final static String PRE_PATIENT_ID = "patientId";
    public final static String PRE_PATIENT_TEL = "patientTel";
    public final static String PRE_PATIENT_NAME = "patientName";
    public final static String PRE_PATIENT_PORTRAIT_URL = "patientPortraitUrl";
    public final static String PRE_PATIENT_MEDICARE_CARD = "patientMedicareCard";
    public final static String PRE_IS_HOME_SHOWING = "isShowingHome";

    private static AIManager aiManager;
    private static SPManager spManager;

    private AIManager() {

    }

    public static AIManager getInstance(Context context) {
        if (aiManager == null) {
            synchronized (AIManager.class) {
                if (aiManager == null) {
                    aiManager = new AIManager();
                    spManager = SPManager.get(context);
                }
            }
        }
        return aiManager;
    }

    public boolean hasLogin() {
        return !TextUtils.isEmpty(getPatientId());
    }

    public void logout() {
        spManager.clear();
    }

    public String getPatientId() {
        return spManager.getString(PRE_PATIENT_ID, "");
    }

    public String getPatientTel() {
        return spManager.getString(PRE_PATIENT_TEL, "");
    }

    public String getPatientPortraitUrl() {
        return spManager.getString(PRE_PATIENT_PORTRAIT_URL, "");
    }

    public String getPatientName() {
        return spManager.getString(PRE_PATIENT_NAME, "");
    }

    public String getMedicareCard() {
        return spManager.getString(PRE_PATIENT_MEDICARE_CARD, "");
    }

    public boolean isHomeShowing() {
        return spManager.getBoolean(PRE_IS_HOME_SHOWING, false);
    }

    public void savePatientInfo(String patientId, String tel, String portraitUrl,String patientName) {
        savePatientId(patientId);
        savePatientTel(tel);
        savePatientPortraitUrl(portraitUrl);
        savePatientName(patientName);
    }

    public void savePatientId(String patientId) {
        spManager.putString(PRE_PATIENT_ID, patientId);
    }

    public void savePatientName(String patientName) {
        spManager.putString(PRE_PATIENT_NAME, patientName);
    }

    public void savePatientPortraitUrl(String portraitUrl) {
        spManager.putString(PRE_PATIENT_PORTRAIT_URL, portraitUrl);
    }

    public void savePatientTel(String tel) {
        spManager.putString(PRE_PATIENT_TEL, tel);
    }

    public void saveMedicareCard(String medicareCard) {
        spManager.putString(PRE_PATIENT_MEDICARE_CARD, medicareCard);
    }

    public void saveHomeShowing(boolean isShowing) {
        spManager.putBoolean(PRE_IS_HOME_SHOWING, isShowing);
    }
}
