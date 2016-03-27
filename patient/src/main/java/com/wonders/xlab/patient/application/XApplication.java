package com.wonders.xlab.patient.application;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.umeng.update.UmengUpdateAgent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends Application {
    public static Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        // Do it on main process
//        if (BuildConfig.DEBUG) {
//            BlockCanary.install(this, new AppBlockCanaryContext()).start();
//        }
        //在这里初始化
        Bugtags.start("b3d8dc7b153c16bb34d741d78b03e4d1", this, Bugtags.BTGInvocationEventShake);

        UmengUpdateAgent.setUpdateOnlyWifi(false);

        // The realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

        EMChat.getInstance().init(this);
        EMChatManager.getInstance().getChatOptions().setShowNotificationInBackgroud(false);//不发通知，而是走广播
        //TODO 在做打包混淆时，要关闭debug模式，避免消耗不必要的资源
        EMChat.getInstance().setDebugMode(false);
    }

}