package com.wonders.xlab.pci.application;

import android.app.Application;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

        // The realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        EMChat.getInstance().init(this);
        EMChatManager.getInstance().getChatOptions().setShowNotificationInBackgroud(false);//不发通知，而是走广播
        EMChat.getInstance().setDebugMode(false);//在做打包混淆时，要关闭debug模式，避免消耗不必要的资源
    }
}