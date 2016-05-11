package com.wonders.xlab.patient.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.medicineremind.list.MedicineRemindActivity;

public class AlarmService extends Service {
    private AlertDialog ad;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,0);

        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("吃了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AlarmService.this, MedicineRemindActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
