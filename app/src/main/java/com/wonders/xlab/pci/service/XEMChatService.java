package com.wonders.xlab.pci.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.TextUtils;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.wonders.xlab.common.utils.MD5Util;
import com.wonders.xlab.common.utils.NotifyUtil;
import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.MainActivity;
import com.wonders.xlab.pci.module.rxbus.ExitBus;
import com.wonders.xlab.pci.receiver.EMChatMessageBroadcastReceiver;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class XEMChatService extends Service {
    private final CompositeSubscription mSubscription;
    private EMChatMessageBroadcastReceiver msgReceiver;

    public XEMChatService() {
        EMChat.getInstance().setAutoLogin(false);
        EMChatManager.getInstance().getChatOptions().setShowNotificationInBackgroud(false);//不发通知，而是走广播

        mSubscription = new CompositeSubscription();

        mSubscription.add(RxBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof ExitBus) {
                    stopSelf();
                }
            }
        }));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new NotifyUtil().showNotification(getApplicationContext(), Constant.NOTIFY_ID, getApplicationContext().getResources().getString(R.string.app_name), "正在运行", MainActivity.class, R.mipmap.ic_launcher, false);

        login();

        return super.onStartCommand(intent, flags, startId);
    }

    private void login() {
        String tel = AIManager.getInstance(this).getUserTel();
        if (TextUtils.isEmpty(tel)) {
            RxBus.getInstance().send(new ExitBus());
            stopSelf();
            return;
        }
        if (msgReceiver == null) {
            msgReceiver = new EMChatMessageBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
            intentFilter.setPriority(13);
            registerReceiver(msgReceiver, intentFilter);
        }
        EMChatManager.getInstance().login(tel, new MD5Util().encrypt("pci_user" + tel).toLowerCase(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMChat.getInstance().setAppInited();
//                EMGroupManager.getInstance().loadAllGroups();
//                EMChatManager.getInstance().loadAllConversations();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                login();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (msgReceiver != null) {
            unregisterReceiver(msgReceiver);
            msgReceiver = null;
        }
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        EMChatManager.getInstance().logout();//此方法为同步方法
    }
}
