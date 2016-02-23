package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.BPAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IBPModel;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/22.
 */
public class BPModel extends DoctorBaseModel {
    private BPAPI mBPAPI;
    private IBPModel mBloodPressureModel;

    public BPModel(IBPModel bloodPressureModel) {
        mBloodPressureModel = bloodPressureModel;
        mBPAPI = mRetrofit.create(BPAPI.class);
    }

    public void getBPList() {
        fetchData(mBPAPI.getBPList(), new ResponseListener() {
            @Override
            public void onSuccess(BaseEntity response) {
                if (mBloodPressureModel != null) {
                    mBloodPressureModel.onReceiveBPSuccess((BPEntity) response);
                }
            }

            @Override
            public void onFailed(Throwable e) {

            }
        });
    }

}
