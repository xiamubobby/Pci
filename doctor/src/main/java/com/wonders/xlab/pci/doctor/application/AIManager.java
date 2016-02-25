package com.wonders.xlab.pci.doctor.application;

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
    public final static String PRE_USER_ID = "userId";
    public final static String PRE_USER_TEL = "userTel";
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
        return !TextUtils.isEmpty(getUserId());
    }

    public void logout() {
        spManager.clear();
    }

    public String getUserId() {
        return spManager.getString(PRE_USER_ID, "");
    }

    public String getUserTel() {
        return spManager.getString(PRE_USER_TEL, "");
    }


    public boolean isHomeShowing() {
        return spManager.getBoolean(PRE_IS_HOME_SHOWING, false);
    }

    public void saveUserInfo(String userId, String tel) {
        saveUserId(userId);
        saveUserTel(tel);
    }

    public void saveUserId(String userId) {
        spManager.putString(PRE_USER_ID, userId);
    }

    public void saveUserTel(String tel) {
        spManager.putString(PRE_USER_TEL, tel);
    }


    public void saveHomeShowing(boolean isShowing) {
        spManager.putBoolean(PRE_IS_HOME_SHOWING, isShowing);
    }
}
