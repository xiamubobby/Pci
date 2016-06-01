package com.wonders.xlab.patient.mvp.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wonders.xlab.patient.data.realm.MedicationUsagesRealm;
import com.wonders.xlab.patient.data.realm.MedicineRemindRealm;
import com.wonders.xlab.patient.module.medicineremind.list.bean.MedicineRemindBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.utils.DateUtil;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 */
public class MedicineRemindCachePresenter extends BasePagePresenter implements MedicineRemindPresenterContract.Actions {

    @Inject
    Realm mRealm;

    private MedicineRemindPresenterContract.ViewListener mViewListener;

    @Inject
    public MedicineRemindCachePresenter(MedicineRemindPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Override
    public void getMedicineReminds(boolean isRefresh) {
        mViewListener.showLoading("");
        final long timeInMillis = Calendar.getInstance().getTimeInMillis();
        RealmResults<MedicineRemindRealm> allSorted = mRealm.where(MedicineRemindRealm.class)
//                .greaterThan("expireTimeInMill", timeInMillis)
                .findAllSorted("remindersTimeInMill", Sort.ASCENDING);
        Observable.from(allSorted)
                .flatMap(new Func1<MedicineRemindRealm, Observable<MedicineRemindBean>>() {
                    @Override
                    public Observable<MedicineRemindBean> call(MedicineRemindRealm medicineRemindRealm) {
                        final MedicineRemindBean bean = new MedicineRemindBean();
                        bean.id.set(medicineRemindRealm.getId());
                        String amOrPm = distinctAMOrPMFromTimeStr(medicineRemindRealm.getRemindersTime());
                        bean.amOrPmStr.set(amOrPm);
                        if (medicineRemindRealm.getExpireTimeInMill() > timeInMillis) {
                            bean.shouldAlarm.set(false);
                        }
                        bean.timeInStr.set(DateUtil.format(medicineRemindRealm.getRemindersTimeInMill(), "hh:mm"));
                        Long endDate = medicineRemindRealm.getEndDate();
                        bean.expiredDateInStr.set(0 == endDate ? "长期" : DateUtil.format(endDate, "yyyy-MM-dd"));
                        Observable.from(medicineRemindRealm.getMedicationUsages())
                                .map(new Func1<MedicationUsagesRealm, String>() {
                                    @Override
                                    public String call(MedicationUsagesRealm usagesRealm) {
                                        return usagesRealm.getMedicationName();
                                    }
                                })
                                .subscribe(new Subscriber<String>() {
                                    StringBuilder mStringBuilder = new StringBuilder();

                                    @Override
                                    public void onCompleted() {
                                        bean.medicineNameStr.set(mStringBuilder.toString().substring(0, mStringBuilder.length() - 1));
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        mViewListener.showToast(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(String s) {
                                        mStringBuilder.append(s).append("，");
                                    }
                                });
                        bean.shouldAlarm.set(medicineRemindRealm.isShouldAlarm());
                        bean.isExpired.set(false);
                        return Observable.just(bean);
                    }
                })
                .subscribe(new Subscriber<MedicineRemindBean>() {
                    List<MedicineRemindBean> medicineRemindBeanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        mViewListener.hideLoading();
                        mViewListener.showMedicineRemind(medicineRemindBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MedicineRemindBean medicineRemindBean) {
                        medicineRemindBeanList.add(medicineRemindBean);
                    }
                });
    }

    @Override
    public void changeRemindState(String remindersRecordId, boolean manualCloseReminder) {
        mRealm.beginTransaction();
        MedicineRemindRealm remindRealm = mRealm.where(MedicineRemindRealm.class).equalTo("id", remindersRecordId).findFirst();
        if (null != remindRealm) {
            remindRealm.setShouldAlarm(!manualCloseReminder);
        }
        mRealm.commitTransaction();
    }

    @NonNull
    private String distinctAMOrPMFromTimeStr(String time) {
        String remindHour = time.split(":")[0];
        String amOrPm = "上午";
        if (TextUtils.isDigitsOnly(remindHour) && Integer.parseInt(remindHour) >= 12) {
            amOrPm = "下午";
        }
        return amOrPm;
    }

}
