package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.BPAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IBPModel;

/**
 * Created by hua on 16/2/22.
 */
public class BPModel extends DoctorBaseModel<BPEntity> {
    private BPAPI mBPAPI;
    private IBPModel mBloodPressureModel;

    public BPModel(IBPModel bloodPressureModel) {
        mBloodPressureModel = bloodPressureModel;
        mBPAPI = mRetrofit.create(BPAPI.class);
    }

    public void getBPList(String patientId, long startTime, long endTime) {
        fetchData(mBPAPI.getBPList(patientId, startTime, endTime));
    }

    @Override
    protected void onSuccess(BPEntity response) {
        mBloodPressureModel.onReceiveBPSuccess(response);
    }

    @Override
    protected void onFailed(Throwable e) {
        mBloodPressureModel.onReceiveFailed(e.getMessage());
    }
}
