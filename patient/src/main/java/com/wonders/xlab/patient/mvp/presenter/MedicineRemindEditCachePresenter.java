package com.wonders.xlab.patient.mvp.presenter;

import android.text.TextUtils;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.data.realm.MedicationUsagesRealm;
import com.wonders.xlab.patient.data.realm.MedicineRemindRealm;
import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;
import com.wonders.xlab.patient.mvp.entity.MedicationUsagesEntity;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;
import com.wonders.xlab.patient.mvp.model.MedicineRemindAddOrModifyModel;
import com.wonders.xlab.patient.mvp.model.MedicineRemindAddOrModifyModelContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.utils.DateUtil;
import io.realm.Realm;
import io.realm.RealmList;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineRemindEditCachePresenter extends BasePresenter implements MedicineRemindEditPresenterContract.Actions, MedicineRemindAddOrModifyModelContract.Callback {
    @Inject
    Realm mRealm;

    @Inject
    AIManager mAIManager;

    private MedicineRemindAddOrModifyModelContract.Actions mAddOrModifyModel;

    private MedicineRemindEditPresenterContract.ViewListener mViewListener;

    @Inject
    public MedicineRemindEditCachePresenter(MedicineRemindEditPresenterContract.ViewListener viewListener, MedicineRemindAddOrModifyModel modifyModel) {
        mAddOrModifyModel = modifyModel;
        mViewListener = viewListener;
        addModel(modifyModel);
    }

    @Override
    public void getMedicineRemindInfoById(final String medicineRemindId) {
        mViewListener.showLoading(Constant.Message.LOADING_PLEASE_WAIT);

        final MedicineRemindRealm remindRealm = mRealm.where(MedicineRemindRealm.class).equalTo("id", medicineRemindId).findFirst();

        Observable.from(remindRealm.getMedicationUsages())
                .map(new Func1<MedicationUsagesRealm, MedicineRealmBean>() {
                    @Override
                    public MedicineRealmBean call(MedicationUsagesRealm medicationUsagesEntity) {
                        MedicineRealmBean bean = new MedicineRealmBean();
                        bean.setMedicineName(medicationUsagesEntity.getMedicationName());
                        bean.setDose(medicationUsagesEntity.getMedicationNum());
                        bean.setFormOfDrug(medicationUsagesEntity.getPharmaceuticalUnit());
                        return bean;
                    }
                })
                .subscribe(new Subscriber<MedicineRealmBean>() {
                    List<MedicineRealmBean> beanList = new ArrayList<>();

                    @Override
                    public void onCompleted() {
                        String[] times = remindRealm.getRemindersTime().split(":");

                        mViewListener.hideLoading();
                        mViewListener.showMedicineRemindInfo(Integer.parseInt(times[0]),
                                Integer.parseInt(times[1]),
                                remindRealm.getStartDate(),
                                remindRealm.getEndDate(),
                                remindRealm.getRemindersDesc(),
                                beanList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MedicineRealmBean medicineRealmBean) {
                        beanList.add(medicineRealmBean);
                    }
                });
    }

    private Calendar calendar = Calendar.getInstance();

    @Override
    public void saveMedicineRemind(MedicineRemindEditBody body) {
        mAddOrModifyModel.addOrModify(mAIManager.getPatientId(),body,this);

        final MedicineRemindRealm remindRealm;
        mRealm.beginTransaction();
        if (TextUtils.isEmpty(body.getId())) {//创建
            long count = mRealm.where(MedicineRemindRealm.class).count();
            //设置id
            body.setId(String.valueOf(count));
            remindRealm = mRealm.createObject(MedicineRemindRealm.class);
        } else {//修改
            //查询
            remindRealm = mRealm.where(MedicineRemindRealm.class).equalTo("id", body.getId()).findFirst();
        }

        remindRealm.setId(body.getId());
        remindRealm.setEndDate(body.getEndDate());
        remindRealm.setShouldAlarm(true);
        remindRealm.setStartDate(body.getStartDate());

        if (body.getEndDate() == 0) {
            remindRealm.setExpireTimeInMill(Long.MAX_VALUE);
        } else {
            String[] times = body.getRemindersTime().split(":");
            calendar.setTimeInMillis(body.getEndDate());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(times[1]));
            remindRealm.setExpireTimeInMill(calendar.getTimeInMillis());
        }

        long remindTime = DateUtil.parseToLong(body.getRemindersTime(), "HH:mm");
        String remindTimeInStr = DateUtil.format(remindTime, "HH:mm");
        remindRealm.setRemindersTime(remindTimeInStr);
        remindRealm.setRemindersTimeInMill(remindTime);

        remindRealm.setRemindersDesc(body.getRemindersDesc());
        Observable.from(body.getMedicationUsages())
                .flatMap(new Func1<MedicationUsagesEntity, Observable<MedicationUsagesRealm>>() {
                    @Override
                    public Observable<MedicationUsagesRealm> call(MedicationUsagesEntity medicationUsagesEntity) {
                        MedicationUsagesRealm usagesRealm = mRealm.createObject(MedicationUsagesRealm.class);
                        usagesRealm.setMedicationName(medicationUsagesEntity.getMedicationName());
                        usagesRealm.setMedicationNum(medicationUsagesEntity.getMedicationNum());
                        usagesRealm.setPharmaceuticalUnit(medicationUsagesEntity.getPharmaceuticalUnit());
                        return Observable.just(usagesRealm);
                    }
                })
                .subscribe(new Subscriber<MedicationUsagesRealm>() {
                    RealmList<MedicationUsagesRealm> medicationUsages = new RealmList<>();

                    @Override
                    public void onCompleted() {
                        remindRealm.setMedicationUsages(medicationUsages);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MedicationUsagesRealm medicationUsagesRealm) {
                        medicationUsages.add(medicationUsagesRealm);
                    }
                });
        mRealm.commitTransaction();
        mViewListener.saveSuccess("保存成功");
    }

    @Override
    public void addOrModifySuccess(String message) {

    }

    @Override
    public void onReceiveFailed(int code, String message) {

    }
}
