package com.wonders.xlab.patient.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.pingplusplus.android.PingppLog;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.wonders.xlab.patient.di.ApplicationComponent;

import io.realm.Realm;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends Application {
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
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        AnalyticsConfig.sEncrypt = true;
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
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
}