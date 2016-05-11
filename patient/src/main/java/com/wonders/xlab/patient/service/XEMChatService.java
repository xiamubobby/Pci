package com.wonders.xlab.patient.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
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
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.MainActivity;
import com.wonders.xlab.patient.otto.ForceExitOtto;
import com.wonders.xlab.patient.receiver.ConnectionBroadcastReceiver;
import com.wonders.xlab.patient.receiver.EMChatMessageBroadcastReceiver;
import com.wonders.xlab.patient.receiver.TimeClickReceiver;

import java.util.Locale;

import im.hua.library.base.BuildConfig;
import im.hua.utils.MD5Util;
import im.hua.utils.NotifyUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class XEMChatService extends Service {
    private EMChatMessageBroadcastReceiver msgReceiver;
    private TimeClickReceiver mTimeClickReceiver;
    private ConnectionBroadcastReceiver mConnectionReceiver;

    public XEMChatService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OttoManager.register(this);
        Notification notification = new NotifyUtil().generateNotification(this, MainActivity.class, null, getResources().getString(R.string.app_name), "正在运行", R.drawable.ic_notification, true, false, false, 0xff30bdf2);
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

        //监听网络变化
        if (mConnectionReceiver == null) {
            mConnectionReceiver = new ConnectionBroadcastReceiver();
        }
        final IntentFilter connectionIntentFilter = new IntentFilter();
        connectionIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnectionReceiver, connectionIntentFilter);
    }

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
    public void forceExit(ForceExitOtto forceExitOtto) {
        new NotifyUtil().cancelAll(this);
        AIManager.getInstance().logout();
        stopSelf();
    }

    /**
     * login em chat
     */
    private void login() {
        String tel = AIManager.getInstance().getPatientTel();
        if (TextUtils.isEmpty(tel)) {
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
        EMChatManager.getInstance().login(tel, new MD5Util().encrypt("pci_user" + tel).toLowerCase(Locale.CHINA), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                if (BuildConfig.DEBUG) Log.d("XEMChatService", "患者登录成功");
                EMChat.getInstance().setAppInited();
                //注册一个监听连接状态的listener
                EMChatManager.getInstance().addConnectionListener(new MyConnectionListener());
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                if (BuildConfig.DEBUG) Log.d("XEMChatService", "患者登录是失败");
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
        if (mConnectionReceiver != null) {
            unregisterReceiver(mConnectionReceiver);
            mConnectionReceiver = null;
        }
        if (mTimeClickReceiver != null) {
            unregisterReceiver(mTimeClickReceiver);
            mTimeClickReceiver = null;
        }
        EMChatManager.getInstance().logout();//此方法为同步方法
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
                                message = "显示帐号已经被移除,请联系客服";
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
