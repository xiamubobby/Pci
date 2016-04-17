package com.wonders.xlab.pci.doctor;

import android.content.Context;

import com.wonders.xlab.common.manager.SPManager;

/**
 * Created by hua on 16/2/19.
 */
public class Constant {
    public static final int NOTIFY_ID = 11111;

    public final static String PREF_KEY_BASE_URL = "base_url";

    public final static String DEFAULT_PORTRAIT = "http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg";
    public final static String DEFAULT_BASE_URL = "http://xlab-tech.com:45675/pci-doctor/";
//    public final static String DEFAULT_BASE_URL = "http://172.16.74.6:8080/pci-doctor/";
//    public final static String DEFAULT_BASE_URL = "http://172.16.77.221:8080/";
//    public final static String DEFAULT_BASE_URL = "http://172.16.77.93:8080/";
    public static String BASE_URL = DEFAULT_BASE_URL;
    public static final String ERROR_MESSAGE = "获取数据失败，请重试！";

    public final static long VIEW_CLICK_SKIP_DURATION = 1000;//ms

    public static void setBaseUrl(Context context) {
        BASE_URL = SPManager.get(context).getString(PREF_KEY_BASE_URL,DEFAULT_BASE_URL);
    }
}