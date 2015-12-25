package com.wonders.xlab.pci.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.util.NetUtils;
import com.wonders.xlab.common.utils.MD5Util;
import com.wonders.xlab.common.utils.NotifyUtil;
import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.MainActivity;
import com.wonders.xlab.pci.module.rxbus.ExitBus;
import com.wonders.xlab.pci.receiver.ConnectionBroadcastReceiver;
import com.wonders.xlab.pci.receiver.EMChatMessageBroadcastReceiver;
import com.wonders.xlab.pci.receiver.TimeClickReceiver;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class XEMChatService extends Service {
    private CompositeSubscription mSubscription;
    private EMChatMessageBroadcastReceiver msgReceiver;
    private TimeClickReceiver mTimeClickReceiver;
    private ConnectionBroadcastReceiver mConnectionReceiver;
    private boolean mIsNormalStop = false;

    public XEMChatService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
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

        initBroadcastReceiver();

        //注册一个监听连接状态的listener
        EMChatManager.getInstance().addConnectionListener(new MyConnectionListener());
    }

    private void initBroadcastReceiver() {
        //监听事件变化
        if (mTimeClickReceiver == null) {
            mTimeClickReceiver = new TimeClickReceiver();
        }
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        intentFilter.setPriority(999);
        registerReceiver(mTimeClickReceiver, intentFilter);

        //监听网络变化
        if (mConnectionReceiver == null) {
            mConnectionReceiver = new ConnectionBroadcastReceiver();
        }
        final IntentFilter connectionIntentFilter = new IntentFilter();
        connectionIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnectionReceiver, connectionIntentFilter);
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
        if (mSubscription == null) {
            mSubscription = new CompositeSubscription();
            mSubscription.add(RxBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                    if (o instanceof ExitBus) {
                        mIsNormalStop = true;
                        stopSelf();
                    }
                }
            }));
        }

        if (!EMChat.getInstance().isLoggedIn()) {
            login();
        }

        new NotifyUtil().showNotification(getApplicationContext(), Constant.NOTIFY_ID, getApplicationContext().getResources().getString(R.string.app_name), "正在运行", MainActivity.class, R.mipmap.ic_launcher, false);

        return START_STICKY;
    }

    /**
     * 登录环信
     */
    private void login() {
        String tel = AIManager.getInstance(this).getUserTel();
        if (TextUtils.isEmpty(tel)) {
            RxBus.getInstance().send(new ExitBus());
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
        EMChatManager.getInstance().login(tel, new MD5Util().encrypt("pci_user" + tel).toLowerCase(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMChat.getInstance().setAppInited();
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
        if (mConnectionReceiver != null) {
            unregisterReceiver(mConnectionReceiver);
            mConnectionReceiver = null;
        }
        if (mTimeClickReceiver != null) {
            unregisterReceiver(mTimeClickReceiver);
            mTimeClickReceiver = null;
        }
        if (mSubscription != null) {
            mSubscription.unsubscribe();
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
                            String message;
                            if (error == EMError.USER_REMOVED) {
                                message = "显示帐号已经被移除,请联系客服";
                            } else if (error == EMError.CONNECTION_CONFLICT) {
                                message = "帐号在其他设备登陆";
                            } else {
                                if (NetUtils.hasNetwork(XEMChatService.this)) {
                                    message = "连接不到聊天服务器,请重新打开应用";
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
