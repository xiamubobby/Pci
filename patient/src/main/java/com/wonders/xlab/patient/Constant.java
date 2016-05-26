package com.wonders.xlab.patient;

/**
 * Created by hua on 15/12/13.
 */
public class Constant {
    /**
     * 常驻通知栏
     */
    public static final int NOTIFY_ID_PERSISTENT = 123456;

    /**
     * 血压理想范围
     */
    public final static String PREF_KEY_IDEAL_BP_RANGE = "pref_key_ideal_bp_range";

    public final static String DEFAULT_PORTRAIT = "http://7xp6gb.com2.z0.glb.qiniucdn.com/2.pic.jpg";

    /**
     * 刘毅地址
     */
//    public static String BASE_URL = "http://172.16.76.210:8080/";
//    public final static String BASE_URL_DEBUG = "http://172.16.76.210:8080/";
    /**
     * 内网地址
     */
//    public static String BASE_URL_DEBUG = "http://172.16.74.6:8080/pci-user/";
    public static String BASE_URL_DEBUG = "http://xlab-tech.com:45675/pci-user/";

    /**
     * 外网地址
     */
    public static String BASE_URL = "http://xlab-tech.com:45675/pci-user/";

    public static class Message {
        public static final String NO_MORE_DATA = "没有更多数据了";
        public static final String LOADING_PLEASE_WAIT = "正在加载，请稍候...";
    }
}