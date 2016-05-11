package com.wonders.xlab.pci.doctor.application;

import android.app.Application;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.wonders.xlab.pci.doctor.di.ApplicationComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        component = ApplicationComponent.Initializer.init(this);

        // Do it on main process
//        if (BuildConfig.DEBUG) {
//            BlockCanary.install(this, new AppBlockCanaryContext()).start();
//        }

        // The realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

        EMChat.getInstance().init(this);
        EMChatManager.getInstance().getChatOptions().setNotifyBySoundAndVibrate(false);//不发通知，而是走广播
        EMChatManager.getInstance().getChatOptions().setShowNotificationInBackgroud(false);
        //TODO 在做打包混淆时，要关闭debug模式，避免消耗不必要的资源
        EMChat.getInstance().setDebugMode(false);
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