package com.wonders.xlab.patient.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.data.realm.MedicineRemindRealm;
import com.wonders.xlab.patient.service.AlarmService;

import java.util.Calendar;

import im.hua.utils.DateUtil;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hua on 16/5/12.
 */
public class AlarmUtil {

    private static AlarmUtil mAlarmUtil;

    private Calendar calendar = Calendar.getInstance();

    public static AlarmUtil newInstance() {
        if (mAlarmUtil == null) {
            synchronized (AlarmUtil.class) {
                if (mAlarmUtil == null) {
                    mAlarmUtil = new AlarmUtil();
                }
            }
        }
        return mAlarmUtil;
    }

    public void scheduleMedicineRemindAlarm(final Context context) {
        final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        final long nowTime = DateUtil.parseToLong(hour + ":" + minute, "HH:mm");

        try {

            XApplication.realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<MedicineRemindRealm> realmResults = realm.where(MedicineRemindRealm.class)
                            .greaterThan("expireTimeInMill", System.currentTimeMillis())
                            .greaterThan("remindersTimeInMill", nowTime)
                            .findAll()
                            .distinct("remindersTimeInMill");

                    for (MedicineRemindRealm remindRealm : realmResults) {
                        String[] timeSplit = remindRealm.getRemindersTime().split(":");
                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
                        calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));

                        Intent intent = new Intent(context, AlarmService.class);
                        intent.putExtra(AlarmService.EXTRA_TIME, remindRealm.getRemindersTimeInMill());
                        PendingIntent mAlarmSender = PendingIntent.getService(context,
                                Integer.parseInt(remindRealm.getId()), intent, 0);

                        if (remindRealm.isShouldAlarm()) {
                            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), mAlarmSender);
                        } else {
                            am.cancel(mAlarmSender);
                        }

                    }
                }
            });

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
