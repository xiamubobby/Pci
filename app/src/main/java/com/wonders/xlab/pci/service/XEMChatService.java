package com.wonders.xlab.pci.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
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
        Log.d("XEMChatService", "emchat service started");
        msgReceiver = new EMChatMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        registerReceiver(msgReceiver, intentFilter);

        EMChatManager.getInstance().login("doctor13248227958", "13248227958", new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                Log.d("XEMChatService", "登陆聊天服务器成功！");

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("XEMChatService", "登陆聊天服务器失败！");
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("XEMChatService", "emchat service destroy");
        if (msgReceiver != null) {
            unregisterReceiver(msgReceiver);
        }
        EMChatManager.getInstance().logout();//此方法为同步方法
    }
}
