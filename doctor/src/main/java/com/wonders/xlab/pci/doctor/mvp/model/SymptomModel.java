package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.SymptomAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ISymptomModel;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/24.
 */
public class SymptomModel extends DoctorBaseModel {
    private SymptomAPI mSymptomAPI;
    private ISymptomModel mISymptomModel;

    public SymptomModel(ISymptomModel iSymptomModel) {
        mISymptomModel = iSymptomModel;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptomList() {
        fetchData(mSymptomAPI.getSymptomList(), new ResponseListener() {
            @Override
            public void onSuccess(BaseEntity response) {
                if (null != mISymptomModel) {
                    mISymptomModel.onReceiveSymptomSuccess((SymptomEntity) response);
                }
            }

            @Override
            public void onFailed(Throwable e) {
                if (null != mISymptomModel) {
                    mISymptomModel.onReceiveFailed(e.getMessage());
                }
            }
        });
    }
}
