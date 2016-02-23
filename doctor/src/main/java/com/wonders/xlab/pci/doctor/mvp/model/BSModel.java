package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.BSAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.BSEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IBSModel;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/22.
 */
public class BSModel extends DoctorBaseModel {
    private BSAPI mBSAPI;
    private IBSModel mBloodPressureModel;

    public BSModel(IBSModel bloodPressureModel) {
        mBloodPressureModel = bloodPressureModel;
        mBSAPI = mRetrofit.create(BSAPI.class);
    }

    public void getBSList() {
        fetchData(mBSAPI.getBSList(), new ResponseListener() {
            @Override
            public void onSuccess(BaseEntity response) {
                if (mBloodPressureModel != null) {
                    mBloodPressureModel.onReceiveBSSuccess((BSEntity) response);
                }
            }

            @Override
            public void onFailed(Throwable e) {

            }
        });
    }

}
