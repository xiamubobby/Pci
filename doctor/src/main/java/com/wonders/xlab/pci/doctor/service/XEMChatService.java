package com.wonders.xlab.pci.doctor.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.util.NetUtils;
import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.MainActivity;
import com.wonders.xlab.pci.doctor.otto.ForceExitOtto;
import com.wonders.xlab.pci.doctor.receiver.EMChatMessageBroadcastReceiver;
import com.wonders.xlab.pci.doctor.receiver.TimeClickReceiver;

import java.util.Locale;

import im.hua.utils.MD5Util;
import im.hua.utils.NotifyUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class XEMChatService extends Service {
    private EMChatMessageBroadcastReceiver msgReceiver;
    private TimeClickReceiver mTimeClickReceiver;
    private boolean mIsNormalStop = false;

    private final int RETRY_TIMES = 10;
    private int mCurrentRetryTime = 0;

    public XEMChatService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OttoManager.register(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("正在运行")
                .setAutoCancel(false);

        Intent intent = new Intent(this, MainActivity.class);
        Bundle data = new Bundle();
        intent.putExtras(data);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        startForeground(Constant.NOTIFY_ID, notification);

        initAutoStart();

        login();

        initBroadcastReceiver();
    }

    private void initBroadcastReceiver() {
        //监听事件变化
        if (mTimeClickReceiver == null) {
            mTimeClickReceiver = new TimeClickReceiver();
        }
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        intentFilter.setPriority(999);
        registerReceiver(mTimeClickReceiver, intentFilter);

    }

    /**
     * 开机自启动
     */
    private void initAutoStart() {
        final PackageManager pm = getPackageManager();
        final ComponentName cn = new ComponentName(getPackageName(), getPackageName() + ".receiver.BootstrapReceiver");
        final int state = pm.getComponentEnabledSetting(cn);
        int newstate;
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            newstate = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
            pm.setComponentEnabledSetting(cn, newstate, PackageManager.DONT_KILL_APP);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Subscribe
    public void forceExit(ForceExitOtto bean) {
        mIsNormalStop = true;
        new NotifyUtil().cancelAll(this);
        SPManager.get(this).clear();
        stopSelf();
    }

    /**
     * 登录环信
     */
    private void login() {
        String tel = AIManager.getInstance(this).getUserTel();
        if (TextUtils.isEmpty(tel)) {
            OttoManager.post(new ForceExitOtto());
            stopSelf();
            return;
        }
        //注册环信消息监听
        if (msgReceiver == null) {
            msgReceiver = new EMChatMessageBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
            intentFilter.setPriority(999);
            registerReceiver(msgReceiver, intentFilter);
        }
        EMChatManager.getInstance().login("doctor" + tel, new MD5Util().encrypt("pci_doctor" + tel).toLowerCase(Locale.CHINA), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                Log.e("XEMChatService", "医生登录成功");
                EMChat.getInstance().setAppInited();
                //注册一个监听连接状态的listener
                EMChatManager.getInstance().addConnectionListener(new MyConnectionListener());
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("XEMChatService", "医生登录失败");
                if (mCurrentRetryTime++ < RETRY_TIMES) {
                    login();
                } else {
                    mCurrentRetryTime = 0;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
        if (msgReceiver != null) {
            unregisterReceiver(msgReceiver);
            msgReceiver = null;
        }
        if (mTimeClickReceiver != null) {
            unregisterReceiver(mTimeClickReceiver);
            mTimeClickReceiver = null;
        }
        EMChatManager.getInstance().logout();//此方法为同步方法

        if (!mIsNormalStop) {
            Intent localIntent = new Intent();
            localIntent.setClass(this, XEMChatService.class);
            startService(localIntent);
        }
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            Observable.just(error)
                    .map(new Func1<Integer, String>() {
                        @Override
                        public String call(Integer integer) {
                            String message = null;
                            if (error == EMError.USER_REMOVED) {
                                message = "帐号已经被移除,请联系客服";
                            } else if (error == EMError.CONNECTION_CONFLICT) {
                                //帐号在其他设备登陆
                                message = "帐号在其他设备登陆";
                                OttoManager.post(new ForceExitOtto());
                            } else {
                                if (NetUtils.hasNetwork(XEMChatService.this)) {
                                    //连接不到聊天服务器,请重新打开应用
                                    message = "";
                                } else {
                                    message = "";
                                }
                            }
                            return message;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String message) {
                            if (!TextUtils.isEmpty(message)) {
                                Toast.makeText(XEMChatService.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

}
