package com.wonders.xlab.patient.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wonders.xlab.patient.util.AlarmUtil;

public class InitialService extends Service {

    AlarmUtil mAlarmUtil = AlarmUtil.newInstance();

    public InitialService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mAlarmUtil.scheduleMedicineRemindAlarm(this);
        startService(new Intent(this, XEMChatService.class));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
