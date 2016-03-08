package com.wonders.xlab.patient.application;

import android.app.Application;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Do it on main process
//        if (BuildConfig.DEBUG) {
//            BlockCanary.install(this, new AppBlockCanaryContext()).start();
//        }

        EMChat.getInstance().init(this);
        EMChatManager.getInstance().getChatOptions().setShowNotificationInBackgroud(false);//不发通知，而是走广播
        //TODO 在做打包混淆时，要关闭debug模式，避免消耗不必要的资源
        EMChat.getInstance().setDebugMode(false);

        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        AnalyticsConfig.enableEncrypt(true);
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity。
    }
}