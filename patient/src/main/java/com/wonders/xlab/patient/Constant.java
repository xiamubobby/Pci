package com.wonders.xlab.patient;

/**
 * Created by hua on 15/12/13.
 */
public class Constant {
    public static final int NOTIFY_ID = 123456;

    /**
     * 血压理想范围
     */
    public final static String PREF_KEY_IDEAL_BP_RANGE = "pref_key_ideal_bp_range";
    /**
     * 血糖理想范围
     */
    public final static String PREF_KEY_IDEAL_BS_RANGE = "pref_key_ideal_bs_range";

    public final static String DEFAULT_PORTRAIT = "http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg";

//    public static String BASE_URL = "http://172.16.77.93:8080/";
    public static String BASE_URL = "http://172.16.74.6:8080/pci-user/";
//    public static String BASE_URL = "http://xlab-tech.com:45675/pci-user/";

    public static String HEALTH_PLAN_URL = BASE_URL + "pci-health-scheme.html?tel=";
}