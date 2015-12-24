package com.wonders.xlab.pci.application;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by hua on 15/12/17.
 * 用户授权信息
 */
public class AIManager {
    /**
     * Preference key
     */
    public final static String PRE_USER_ID = "userId";
    public final static String PRE_USER_TEL = "userTel";
    public final static String PRE_USER_MEDICARE_CARD = "userMedicareCard";

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
        return !TextUtils.isEmpty(getUserId());
    }

    public String getUserId() {
        return spManager.getString(PRE_USER_ID, "");
    }

    public String getUserTel() {
        return spManager.getString(PRE_USER_TEL, "");
    }

    public String getMedicareCard() {
        return spManager.getString(PRE_USER_MEDICARE_CARD, "");
    }

    public void saveUserInfo(String userId, String tel, String medicareCard) {
        saveUserId(userId);
        saveUserTel(tel);
        saveMedicareCard(medicareCard);
    }

    public void saveUserId(String userId) {
        spManager.putString(PRE_USER_ID, userId);
    }

    public void saveUserTel(String tel) {
        spManager.putString(PRE_USER_TEL, tel);
    }

    public void saveMedicareCard(String medicareCard) {
        spManager.putString(PRE_USER_MEDICARE_CARD, medicareCard);
    }
}
