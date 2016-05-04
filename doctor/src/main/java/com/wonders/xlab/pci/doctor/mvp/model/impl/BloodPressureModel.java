package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.BPAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;
import com.wonders.xlab.pci.doctor.mvp.model.IBloodPressureModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/4/1.
 */
public class BloodPressureModel extends DoctorBaseModel<BPEntity> implements IBloodPressureModel {
    private BPAPI mBPAPI;
    private BPModelListener mListener;

    public BloodPressureModel(BPModelListener listener) {
        mListener = listener;
        mBPAPI = mRetrofit.create(BPAPI.class);
    }

    public void getBPList(String patientId, long startTime, long endTime, int page, int size) {
        request(mBPAPI.getBPList(patientId, startTime, endTime, page, size), true);}

    @Override
    protected void onSuccess(BPEntity response) {
        mListener.onReceiveBPSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    public interface BPModelListener extends BaseModelListener {
        void onReceiveBPSuccess(BPEntity bpEntity);
    }
}
