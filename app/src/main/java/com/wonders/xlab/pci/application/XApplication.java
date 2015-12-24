package com.wonders.xlab.pci.application;

import com.activeandroid.ActiveAndroid;
import com.bugtags.library.Bugtags;
import com.easemob.chat.EMChat;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        Bugtags.start("b3d8dc7b153c16bb34d741d78b03e4d1", this, Bugtags.BTGInvocationEventNone);

        LeakCanary.install(this);

        EMChat.getInstance().init(this);
        EMChat.getInstance().setDebugMode(true);//在做打包混淆时，要关闭debug模式，避免消耗不必要的资源

        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        AnalyticsConfig.enableEncrypt(true);
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity。

    }
}
