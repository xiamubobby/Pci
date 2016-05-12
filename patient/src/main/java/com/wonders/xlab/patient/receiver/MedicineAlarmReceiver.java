package com.wonders.xlab.patient.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.data.realm.MedicineRemindRealm;
import com.wonders.xlab.patient.module.medicineremind.list.MedicineRemindActivity;
import com.wonders.xlab.patient.util.AlarmUtil;

import io.realm.RealmResults;

/**
 * Created by hua on 16/5/12.
 */
public class MedicineAlarmReceiver extends BroadcastReceiver {
    public final static String EXTRA_TIME = "time";

    AlarmUtil mAlarmUtil = AlarmUtil.newInstance();

    private AlertDialog ad;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Log.d("MedicineAlarmReceiver", "receiver");
        if (null == intent) {
            return;
        }
        long remindTime = intent.getLongExtra(EXTRA_TIME,0);
        Log.d("MedicineAlarmReceiver", "remindTime:" + remindTime);
        RealmResults<MedicineRemindRealm> remindRealmResults = XApplication.realm.where(MedicineRemindRealm.class)
                .equalTo("remindersTimeInMill", remindTime)
                .findAll();

        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);

        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("该吃药了")
                .setPositiveButton("吃了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(mContext, MedicineRemindActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);

                    }
                }).setNegativeButton("暂时不吃", null);

        if (ad != null && ad.isShowing()) {
            ad.dismiss();
            ad = null;

        }
        ad = builder.create();
        ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        ad.show();
        Log.d("AlarmService", "start alarm");

        mAlarmUtil.scheduleMedicineRemindAlarm(context);
    }
}
