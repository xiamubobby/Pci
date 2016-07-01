package com.wonders.xlab.patient.module.healthchart.bp;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.BPAPI;
import com.wonders.xlab.patient.mvp.entity.BloodPressureEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/1.
 */
public class BloodPressureModel extends PatientBaseModel<BloodPressureEntity> implements IBloodPressureModel {
    private BPAPI mBPAPI;
    private BPModelListener mListener;

    public BloodPressureModel(BPModelListener listener) {
        mListener = listener;
        mBPAPI = mRetrofit.create(BPAPI.class);
    }

    public void getBPList(String patientId, long startTime, long endTime, int page, int size) {
        request(mBPAPI.getBPList(patientId, startTime, endTime, page, size), true);}

    @Override
    protected void onSuccess(BloodPressureEntity response) {
        mListener.onReceiveBPSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    public interface BPModelListener extends BaseModelListener {
        void onReceiveBPSuccess(BloodPressureEntity bpEntity);
    }
}
