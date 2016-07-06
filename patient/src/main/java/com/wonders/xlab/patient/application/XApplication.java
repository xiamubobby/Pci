package com.wonders.xlab.patient.application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.marswin89.marsdaemon.DaemonApplication;
import com.marswin89.marsdaemon.DaemonConfigurations;
import com.pingplusplus.android.PingppLog;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.wonders.xlab.patient.immortalize.Receiver1;
import com.wonders.xlab.patient.immortalize.Receiver2;
import com.wonders.xlab.patient.immortalize.Service2;
import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.service.XEMChatService;

import io.realm.Realm;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends DaemonApplication {
    public static Realm realm;
    private static ApplicationComponent component;
    private boolean mHasShowed = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        // Do it on main process
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        component = ApplicationComponent.Initializer.init(this);
        realm = component.getRealm();

        PingppLog.DEBUG = false;

        MobclickAgent.setDebugMode(false);
        UmengUpdateAgent.setUpdateOnlyWifi(true);

        AnalyticsConfig.sEncrypt = true;
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity

    }

    @Override
    protected DaemonConfigurations getDaemonConfigurations() {
        DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration(
                "com.wonders.xlab.patient:process1",
                XEMChatService.class.getCanonicalName(),
                Receiver1.class.getCanonicalName());

        DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration(
                "com.wonders.xlab.patient:process2",
                Service2.class.getCanonicalName(),
                Receiver2.class.getCanonicalName());

        DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
        //return new DaemonConfigurations(configuration1, configuration2);//listener can be null
        return new DaemonConfigurations(configuration1, configuration2, listener);
    }

    @Override
    public void attachBaseContextByDaemon(Context base) {
        MultiDex.install(this);
    }

    public static ApplicationComponent getComponent() {
        return component;
    }

    public boolean showSplash() {
        return !mHasShowed;
    }

    public void setHasShowed(boolean hasShowed) {
        mHasShowed = hasShowed;
    }

    class MyDaemonListener implements DaemonConfigurations.DaemonListener{
        @Override
        public void onPersistentStart(Context context) {
        }

        @Override
        public void onDaemonAssistantStart(Context context) {
        }

        @Override
        public void onWatchDaemonDaed() {
        }
    }
}