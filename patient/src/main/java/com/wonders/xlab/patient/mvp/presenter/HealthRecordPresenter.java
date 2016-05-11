package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.mvp.entity.RealNameValidateEntity;
import com.wonders.xlab.patient.mvp.model.RealNameValidateModel;
import com.wonders.xlab.patient.mvp.model.RealNameValidateModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/11.
 */
public class HealthRecordPresenter extends BasePresenter implements HealthRecordPresenterContract.Actions, RealNameValidateModelContract.Callback {
    @Inject
    AIManager mAIManager;
    private HealthRecordPresenterContract.ViewListener mViewListener;
    private RealNameValidateModelContract.Actions mRealNameModel;

    @Inject
    public HealthRecordPresenter(HealthRecordPresenterContract.ViewListener viewListener, RealNameValidateModel realNameModel) {
        mViewListener = viewListener;
        mRealNameModel = realNameModel;
        addModel(mRealNameModel);
    }

    @Override
    public void getValidateResult() {
        mRealNameModel.getValidateResult(mAIManager.getPatientId(), this);
    }

    @Override
    public void onReceiveValidateResultSuccess(RealNameValidateEntity entity) {
        mViewListener.showResultMessage(entity.getMessage());
        mViewListener.showValidateButton(entity.getRet_values() == 0 || entity.getRet_values() == 3);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}
