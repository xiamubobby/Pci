package com.wonders.xlab.patient.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.data.realm.MedicationUsagesRealm;
import com.wonders.xlab.patient.data.realm.MedicineRemindRealm;
import com.wonders.xlab.patient.util.AlarmUtil;

import java.util.ArrayList;
import java.util.List;

import im.hua.utils.DateUtil;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class AlarmService extends Service {
    public final static String EXTRA_TIME = "time";

    AlarmUtil mAlarmUtil = AlarmUtil.newInstance();

    private AlertDialog.Builder builder;

    private AlertDialog ad;

    private MedicineRemindDialogRVAdapter adapter;
    private View container;


    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long remindTime = intent.getLongExtra(EXTRA_TIME, 0);
        RealmResults<MedicineRemindRealm> remindRealmResults = XApplication.realm.where(MedicineRemindRealm.class)
                .equalTo("remindersTimeInMill", remindTime)
                .findAll();

        try {
            MedicineRemindRealm medicineRemindRealm = remindRealmResults.first();

            final List<MedicationUsagesRealm> usagesRealmList = new ArrayList<>();
            Observable.from(remindRealmResults)
                    .flatMap(new Func1<MedicineRemindRealm, Observable<MedicationUsagesRealm>>() {
                        @Override
                        public Observable<MedicationUsagesRealm> call(MedicineRemindRealm medicineRemindRealm) {
                            return Observable.from(medicineRemindRealm.getMedicationUsages());
                        }
                    })
                    .subscribe(new Subscriber<MedicationUsagesRealm>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(MedicationUsagesRealm usagesRealm) {
                            usagesRealmList.add(usagesRealm);
                        }
                    });
            if (adapter == null) {
                adapter = new MedicineRemindDialogRVAdapter();
            }
            adapter.setDatas(usagesRealmList);
            if (container == null) {
                container = LayoutInflater.from(this).inflate(R.layout.medicine_remind_dialog, null, false);
                TextView textView = (TextView) container.findViewById(R.id.text_time);

                textView.setText(String.format("%s %s", distinctAMOrPMFromTimeStr(medicineRemindRealm.getRemindersTime()), DateUtil.format(remindTime, "hh:mm")));

                RecyclerView recyclerView = (RecyclerView) container.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }

            if (null == builder) {
                builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                builder.setIcon(R.mipmap.ic_launcher)
                        .setView(container)
                        .setPositiveButton("吃了", null);

                if (ad != null && ad.isShowing()) {
                    ad.dismiss();
                    ad = null;
                }
                ad = builder.create();
                ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            }
            ad.setCanceledOnTouchOutside(false);
            ad.setTitle(medicineRemindRealm.getRemindersDesc());
            ad.show();
            mAlarmUtil.scheduleMedicineRemindAlarm(this);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private String distinctAMOrPMFromTimeStr(String time) {
        String remindHour = time.split(":")[0];
        String amOrPm = "上午";
        if (TextUtils.isDigitsOnly(remindHour) && Integer.parseInt(remindHour) >= 12) {
            amOrPm = "下午";
        }
        return amOrPm;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
