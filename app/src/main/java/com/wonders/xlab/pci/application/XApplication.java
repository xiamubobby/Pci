package com.wonders.xlab.pci.application;

import com.activeandroid.ActiveAndroid;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.github.moduth.blockcanary.BlockCanary;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.application.AppBlockCanaryContext;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Do it on main process
        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        ActiveAndroid.initialize(this);

        EMChat.getInstance().init(this);
        EMChatManager.getInstance().getChatOptions().setShowNotificationInBackgroud(false);//不发通知，而是走广播
        EMChat.getInstance().setDebugMode(false);//在做打包混淆时，要关闭debug模式，避免消耗不必要的资源

        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        AnalyticsConfig.enableEncrypt(true);
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity。
    }
}