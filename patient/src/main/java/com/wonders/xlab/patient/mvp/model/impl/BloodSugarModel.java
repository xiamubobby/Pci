package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.BSAPI;
import com.wonders.xlab.patient.mvp.entity.BloodSugarEntity;
import com.wonders.xlab.patient.mvp.model.IBloodSugarModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/1.
 */
public class BloodSugarModel extends PatientBaseModel<BloodSugarEntity> implements IBloodSugarModel{
    private BSAPI mBSAPI;
    private BloodSugarModelListener mBloodSugarModelListener;

    public BloodSugarModel(BloodSugarModelListener bloodSugarModelListener) {
        mBloodSugarModelListener = bloodSugarModelListener;
        mBSAPI = mRetrofit.create(BSAPI.class);
    }

    @Override
    protected void onSuccess(BloodSugarEntity response) {
        mBloodSugarModelListener.onReceiveBSSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mBloodSugarModelListener.onReceiveFailed(code, message);
    }

    @Override
    public void getBSList(String userId, int page, int pageSize) {
        request(mBSAPI.getBSList(userId, page, pageSize),true);
    }

    public interface BloodSugarModelListener extends BaseModelListener {
        void onReceiveBSSuccess(BloodSugarEntity bsEntity);
    }
}
