package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.SymptomAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ISymptomModel;

/**
 * Created by hua on 16/2/24.
 */
public class SymptomModel extends DoctorBaseModel<SymptomEntity> {
    private SymptomAPI mSymptomAPI;
    private ISymptomModel mISymptomModel;

    public SymptomModel(ISymptomModel iSymptomModel) {
        mISymptomModel = iSymptomModel;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptomList() {
        fetchData(mSymptomAPI.getSymptomList());
    }

    @Override
    protected void onSuccess(SymptomEntity response) {
        mISymptomModel.onReceiveSymptomSuccess(response);
    }

    @Override
    protected void onFailed(Throwable e) {
        mISymptomModel.onReceiveFailed(e.getMessage());
    }
}
