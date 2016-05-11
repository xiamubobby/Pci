package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.data.realm.MedicationUsagesRealm;
import com.wonders.xlab.patient.data.realm.MedicineRemindRealm;
import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;
import com.wonders.xlab.patient.mvp.entity.MedicationUsagesEntity;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindDetailEntity;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;
import com.wonders.xlab.patient.mvp.model.MedicineRemindAddOrModifyModel;
import com.wonders.xlab.patient.mvp.model.MedicineRemindAddOrModifyModelContract;
import com.wonders.xlab.patient.mvp.model.MedicineRemindDetailModel;
import com.wonders.xlab.patient.mvp.model.MedicineRemindDetailModelContract;

import java.util.ArrayList;
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
public class MedicineRemindEditPresenter extends BasePresenter implements MedicineRemindEditPresenterContract.Actions, MedicineRemindAddOrModifyModelContract.Callback, MedicineRemindDetailModelContract.Callback {
    @Inject
    AIManager mAIManager;
    @Inject
    Realm mRealm;

    private MedicineRemindEditPresenterContract.ViewListener mViewListener;
    private MedicineRemindAddOrModifyModelContract.Actions mAddOrModifyModel;
    private MedicineRemindDetailModelContract.Actions mDetailModel;

    private boolean mIsSaving;

    @Inject
    public MedicineRemindEditPresenter(MedicineRemindAddOrModifyModel addOrModifyModel, MedicineRemindEditPresenterContract.ViewListener viewListener, MedicineRemindDetailModel detailModel) {
        mViewListener = viewListener;
        mAddOrModifyModel = addOrModifyModel;
        mDetailModel = detailModel;
        addModel(mAddOrModifyModel);
        addModel(mDetailModel);
    }

    @Override
    public void getMedicineRemindInfoById(String medicineRemindId) {
        mViewListener.showLoading(Constant.Message.LOADING_PLEASE_WAIT);
        mDetailModel.getRemindDetail(medicineRemindId, this);
    }

    @Override
    public void addOrModify(MedicineRemindEditBody body) {
        //cache
        mRealm.beginTransaction();
        final MedicineRemindRealm remindRealm = mRealm.createObject(MedicineRemindRealm.class);
        remindRealm.setId(body.getId());
        remindRealm.setEndDate(body.getEndDate());
        remindRealm.setStartDate(body.getStartDate());

        long remindTime = DateUtil.parseToLong(body.getRemindersTime(), "HH:mm");
        remindRealm.setRemindersTime(DateUtil.format(remindTime, "hh:mm"));

        remindRealm.setRemindersTimeInMill(DateUtil.parseToLong(body.getRemindersTime(), "HH:mm"));
        remindRealm.setRemindersDesc(body.getRemindersDesc());
        Observable.from(body.getMedicationUsages())
                .flatMap(new Func1<MedicationUsagesEntity, Observable<MedicationUsagesRealm>>() {
                    @Override
                    public Observable<MedicationUsagesRealm> call(MedicationUsagesEntity medicationUsagesEntity) {
                        MedicationUsagesRealm usagesRealm = new MedicationUsagesRealm();
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

        //save to remote server
        if (mIsSaving) {
            mViewListener.showErrorToast("正在保存，请稍候重试...");
            return;
        }
        mViewListener.showLoading("正在保存，请稍候...");
        mIsSaving = true;
        mAddOrModifyModel.addOrModify(mAIManager.getPatientId(), body, this);
    }

    @Override
    public void addOrModifySuccess(String message) {
        mIsSaving = false;
        mViewListener.hideLoading();
        mViewListener.saveSuccess(message);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mIsSaving = false;
        showError(mViewListener, code, message);
    }

    @Override
    public void OnReceiveMedicineRemindDetailSuccess(MedicineRemindDetailEntity entity) {
        mViewListener.hideLoading();
        final MedicineRemindDetailEntity.RetValuesEntity retValues = entity.getRet_values();

        Observable.from(retValues.getMedicationUsages())
                .map(new Func1<MedicationUsagesEntity, MedicineRealmBean>() {
                    @Override
                    public MedicineRealmBean call(MedicationUsagesEntity medicationUsagesEntity) {
                        MedicineRealmBean bean = new MedicineRealmBean();
                        bean.setMedicineName(medicationUsagesEntity.getMedicationName());
                        bean.setDose(medicationUsagesEntity.getMedicationNum());
                        bean.setFormOfDrug(medicationUsagesEntity.getPharmaceuticalUnit());
                        return bean;
                    }
                })
                .subscribe(new Subscriber<MedicineRealmBean>() {
                    List<MedicineRealmBean> beanList = new ArrayList<>();

                    String[] times = retValues.getRemindersTime().split(":");

                    @Override
                    public void onCompleted() {
                        mViewListener.showMedicineRemindInfo(Integer.parseInt(times[0]),
                                Integer.parseInt(times[1]),
                                retValues.getStartDate(),
                                retValues.getEndDate(),
                                retValues.getRemindersDesc(),
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
}
