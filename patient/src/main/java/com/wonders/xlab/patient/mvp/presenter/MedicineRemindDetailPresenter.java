package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.MedicineBean;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;
import com.wonders.xlab.patient.mvp.model.MedicineRemindAddOrModifyModel;
import com.wonders.xlab.patient.mvp.model.MedicineRemindAddOrModifyModelContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineRemindDetailPresenter extends BasePresenter implements MedicineRemindDetailPresenterContract.Actions, MedicineRemindAddOrModifyModelContract.Callback {
    private MedicineRemindDetailPresenterContract.ViewListener mViewListener;
    private MedicineRemindAddOrModifyModelContract.Actions mAddOrModifyModel;

    @Inject
    public MedicineRemindDetailPresenter(MedicineRemindDetailPresenterContract.ViewListener viewListener, MedicineRemindAddOrModifyModel addOrModifyModel) {
        mViewListener = viewListener;
        mAddOrModifyModel = addOrModifyModel;
    }

    @Override
    public void getMedicineRemindInfoById(String medicineRemindId) {
        mViewListener.showLoading("");

        List<MedicineBean> beanList = new ArrayList<>();
        mViewListener.showMedicineRemindInfo(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().getTimeInMillis(), null, "该吃药了", beanList);
    }

    @Override
    public void addOrModify(MedicineRemindEditBody body) {
        mAddOrModifyModel.addOrModify(body, this);
    }

    @Override
    public void addOrModifySuccess(String message) {
        mViewListener.hideLoading();
        mViewListener.saveSuccess(message);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}
