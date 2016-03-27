package com.wonders.xlab.pci.doctor.application;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;

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
        //在这里初始化
        Bugtags.start("08cac51dafe59b7c0682fd0acc983332", this, Bugtags.BTGInvocationEventShake);

        EMChat.getInstance().init(this);
        EMChatManager.getInstance().getChatOptions().setShowNotificationInBackgroud(false);//不发通知，而是走广播
        //TODO 在做打包混淆时，要关闭debug模式，避免消耗不必要的资源
        EMChat.getInstance().setDebugMode(false);
    }
}