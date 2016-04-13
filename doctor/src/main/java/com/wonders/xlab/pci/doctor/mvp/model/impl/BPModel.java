package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.BPAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/22.
 */
public class BPModel extends DoctorBaseModel<BPEntity> {
    private BPAPI mBPAPI;
    private BPModelListener mBloodPressureModel;

    public BPModel(BPModelListener bloodPressureModel) {
        mBloodPressureModel = bloodPressureModel;
        mBPAPI = mRetrofit.create(BPAPI.class);
    }

    public void getBPList(String patientId, long startTime, long endTime) {
        fetchData(mBPAPI.getBPList(patientId, startTime, endTime), true);
    }

    @Override
    protected void onSuccess(BPEntity response) {
        mBloodPressureModel.onReceiveBPSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mBloodPressureModel.onReceiveFailed(code, "获取血压数据失败，请重试！");
    }

    public interface BPModelListener extends BaseModelListener {
        void onReceiveBPSuccess(BPEntity bpEntity);
    }
}
