package com.wonders.xlab.pci.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.wonders.xlab.common.utils.MD5Util;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.rxbus.ExitBus;
import com.wonders.xlab.pci.receiver.EMChatMessageBroadcastReceiver;

public class XEMChatService extends Service {
    private EMChatMessageBroadcastReceiver msgReceiver;

    public XEMChatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String tel = AIManager.getInstance(this).getUserTel();
        if (TextUtils.isEmpty(tel)) {
            RxBus.getInstance().send(new ExitBus());
            stopSelf();
            return 0;
        }

        if (msgReceiver == null) {
            msgReceiver = new EMChatMessageBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
            intentFilter.setPriority(3);
            registerReceiver(msgReceiver, intentFilter);
        }

        EMChatManager.getInstance().login(tel, new MD5Util().encrypt("pci_user" + tel).toLowerCase(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("XEMChatService", "登陆聊天服务器失败！");
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (msgReceiver != null) {
            unregisterReceiver(msgReceiver);
            msgReceiver = null;
        }
        EMChatManager.getInstance().logout();//此方法为同步方法
    }
}
