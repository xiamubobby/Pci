package com.wonders.xlab.pci.doctor.application;

import android.content.Context;
import android.text.TextUtils;

import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.pci.doctor.Constant;

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
    public final static String PRE_USER_NAME = "userName";
    public final static String PRE_USER_AVATAR_URL = "avatarUrl";
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

    public String getUserName() {

        return spManager.getString(PRE_USER_NAME, "");
    }

    public String getAvatarUrl() {
        return spManager.getString(PRE_USER_AVATAR_URL, Constant.DEFAULT_PORTRAIT);
    }


    public boolean isHomeShowing() {
        return spManager.getBoolean(PRE_IS_HOME_SHOWING, false);
    }

    public void saveUserInfo(String userId, String tel, String avatarUrl,String userName) {
        saveUserId(userId);
        saveUserTel(tel);
        saveAvatarUrl(avatarUrl);
        saveUserName(userName);
    }

    public void saveUserId(String userId) {

        spManager.putString(PRE_USER_ID, userId);
    }

    public void saveUserName(String userName) {
        spManager.putString(PRE_USER_NAME, userName);
    }

    public void saveUserTel(String tel) {
        spManager.putString(PRE_USER_TEL, tel);
    }

    public void saveAvatarUrl(String avatarUrl) {
        spManager.putString(PRE_USER_AVATAR_URL, avatarUrl);
    }


    public void saveHomeShowing(boolean isShowing) {
        spManager.putBoolean(PRE_IS_HOME_SHOWING, isShowing);
    }
}