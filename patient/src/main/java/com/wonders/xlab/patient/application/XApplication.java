package com.wonders.xlab.patient.application;

import android.app.Application;
import android.content.Intent;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.wonders.xlab.patient.di.ApplicationComponent;
import com.wonders.xlab.patient.service.XEMChatService;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
        LeakCanary.install(this);
        // Do it on main process
        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        component = ApplicationComponent.Initializer.init(this);

        MobclickAgent.setDebugMode(false);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        AnalyticsConfig.sEncrypt = true;
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity

        // The realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

        /**
         * 延时启动环信聊天服务和用药提醒
         */
        Observable.just(null)
                .delaySubscription(5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        component.getAlarmUtil().scheduleMedicineRemindAlarm(XApplication.this);
                        startService(new Intent(XApplication.this, XEMChatService.class));
                    }
                });
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public boolean showSplash() {
        return !mHasShowed;
    }

    public void setHasShowed(boolean hasShowed) {
        mHasShowed = hasShowed;
    }
}