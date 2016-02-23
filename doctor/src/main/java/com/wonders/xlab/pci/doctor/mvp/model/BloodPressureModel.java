package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.BloodPressureAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IBloodPressureModel;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/22.
 */
public class BloodPressureModel extends DoctorBaseModel {
    private BloodPressureAPI mBloodPressureAPI;
    private IBloodPressureModel mBloodPressureModel;

    public BloodPressureModel(IBloodPressureModel bloodPressureModel) {
        mBloodPressureModel = bloodPressureModel;
        mBloodPressureAPI = mRetrofit.create(BloodPressureAPI.class);
    }

    public void getBPList() {
        fetchData(mBloodPressureAPI.getBPList(), new ResponseListener() {
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
