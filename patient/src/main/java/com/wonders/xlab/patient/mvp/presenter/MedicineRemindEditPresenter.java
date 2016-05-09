package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.medicineremind.MedicineBean;
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
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineRemindEditPresenter extends BasePresenter implements MedicineRemindEditPresenterContract.Actions, MedicineRemindAddOrModifyModelContract.Callback, MedicineRemindDetailModelContract.Callback {
    @Inject
    AIManager mAIManager;

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
                .map(new Func1<MedicineRemindDetailEntity.RetValuesEntity.MedicationUsagesEntity, MedicineBean>() {
                    @Override
                    public MedicineBean call(MedicineRemindDetailEntity.RetValuesEntity.MedicationUsagesEntity medicationUsagesEntity) {
                        MedicineBean bean = new MedicineBean();
                        bean.setMedicineName(medicationUsagesEntity.getMedicationName());
                        bean.setDose(medicationUsagesEntity.getMedicationNum());
                        bean.setFormOfDrug(medicationUsagesEntity.getPharmaceuticalUnit());
                        return bean;
                    }
                })
                .subscribe(new Subscriber<MedicineBean>() {
                    List<MedicineBean> beanList = new ArrayList<>();

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
                    public void onNext(MedicineBean medicineBean) {
                        beanList.add(medicineBean);
                    }
                });


    }
}
