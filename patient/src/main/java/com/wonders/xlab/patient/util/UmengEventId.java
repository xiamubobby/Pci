package com.wonders.xlab.patient.util;

/**
 * Created by hua on 16/4/6.
 */
public class UmengEventId {
    /**
     * 计数事件
     */
    public final static String HOME_TOP_IMAGE = "home_top_image";//首页顶部推广图片点击数
    public final static String HOME_TOP_CIRCLE_BS = "home_top_circle_bs";//首页顶部血糖圆圈点击数
    public final static String HOME_TOP_CIRCLE_BP = "home_top_circle_bp";//首页顶部血压圆圈点击数
    public final static String HOME_TOP_CIRCLE_HEART_RATE = "home_top_circle_heart_rate";//首页顶部心率圆圈点击数
    public final static String HOME_MEDICINE_REMIND = "home_medicine_remind";//首页用药提醒模块点击数
    public final static String HOME_DAILY_RECORD = "home_daily_record";//首页每日记录模块点击数
    public final static String HOME_DAILY_RECORD_BLANK_MEASURE_BP = "home_daily_record_blank_measure_bp";//每日记录血压界面，点击测一测次数
    public final static String HOME_DAILY_RECORD_BLANK_MEASURE_BS = "home_daily_record_blank_measure_bs";//每日记录血糖界面，点击测一测次数
    public final static String HOME_DAILY_RECORD_MENU_MEASURE_BP = "home_daily_record_menu_measure_bp";//每日记录菜单，点击测量血压次数
    public final static String HOME_DAILY_RECORD_MENU_MEASURE_BS = "home_daily_record_menu_measure_bs";//每日记录菜单，点击测量血糖次数
    public final static String HOME_DAILY_RECORD_MENU_MEASURE_SYMPTOM = "home_daily_record_menu_measure_symptom";//每日记录菜单，点击不适症状次数
    public final static String HOME_DAILY_RECORD_DEFAULT_MEASURE_EQUIPMENT = "home_daily_record_default_measure_equipment";//设置为默认全程健康设备测量方式次数
    public final static String HOME_DAILY_RECORD_DEFAULT_MEASURE_MANUAL = "home_daily_record_default_measure_equipment";//设置为默认手动测量方式次数
    public final static String HOME_DAILY_RECORD_MEASURE_DIRECT_BP = "home_daily_record_measure_direct_bp";//设备测量界面，点击直接测量血压次数
    public final static String HOME_DAILY_RECORD_MEASURE_DIRECT_BS = "home_daily_record_measure_direct_bs";//设备测量界面，点击直接测量血糖次数
    public final static String HOME_DAILY_RECORD_MEASURE_RETRY_BP = "home_daily_record_measure_retry_bp";//设备测量界面，点击再次测量血压次数
    public final static String HOME_DAILY_RECORD_MEASURE_RETRY_BS = "home_daily_record_measure_retry_bs";//设备测量界面，点击再次测量血糖次数

    /**
     * 计算事件
     */
    public final static String HOME_DAILY_RECORD_MEASURE_EQUIPMENT_COST_TIME_BS = "home_daily_record_measure_equipment_cost_time_bs";//从开始连接血糖设备到保存成功数据所花费的时间
    public final static String HOME_DAILY_RECORD_MEASURE_EQUIPMENT_COST_TIME_BP = "home_daily_record_measure_equipment_cost_time_bp";//从开始连接血压设备到保存成功数据所花费的时间
    public final static String HOME_DAILY_RECORD_MEASURE_EQUIPMENT_CONNECT_COST_TIME_BS = "home_daily_record_measure_equipment_connect_cost_time_bs";//从开始连接血糖设备到连接成功所花费的时间
    public final static String HOME_DAILY_RECORD_MEASURE_EQUIPMENT_CONNECT_COST_TIME_BP = "home_daily_record_measure_equipment_connect_cost_time_bp";//从开始连接血压设备到连接成功所花费的时间
    public final static String HOME_DAILY_RECORD_MEASURE_EQUIPMENT_CONNECT_FAILED_TIME_BP = "home_daily_record_measure_equipment_connect_failed_time_bp";//同一次设备测量直至退出血压界面还失败的次数
    public final static String HOME_DAILY_RECORD_MEASURE_EQUIPMENT_CONNECT_FAILED_TIME_BS = "home_daily_record_measure_equipment_connect_failed_time_bs";//同一次设备测量直至退出血糖界面还失败的次数

}
