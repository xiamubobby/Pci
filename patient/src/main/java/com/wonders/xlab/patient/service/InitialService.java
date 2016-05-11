package com.wonders.xlab.patient.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wonders.xlab.patient.data.realm.MedicineRemindRealm;

import java.util.Calendar;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.Sort;

public class InitialService extends Service {
    @Inject
    Realm mRealm;

    public InitialService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MedicineRemindRealm remindRealm = mRealm.where(MedicineRemindRealm.class).greaterThan("endDate",Calendar.getInstance().getTimeInMillis()).findAllSorted("remindersTimeInMill", Sort.ASCENDING).first();
        if (null != remindRealm) {

        }


        PendingIntent mAlarmSender = PendingIntent.getService(this,
                0, new Intent(this, AlarmService.class), 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 3 * 1000, mAlarmSender);

        startService(new Intent(this, XEMChatService.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
