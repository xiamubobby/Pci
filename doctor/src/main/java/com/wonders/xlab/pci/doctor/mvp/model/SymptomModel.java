package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.SymptomAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.SymptomModelListener;

/**
 * Created by hua on 16/2/24.
 */
public class SymptomModel extends DoctorBaseModel<SymptomEntity> {
    private SymptomAPI mSymptomAPI;
    private SymptomModelListener mISymptomModel;

    public SymptomModel(SymptomModelListener iSymptomModel) {
        mISymptomModel = iSymptomModel;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptomList(String userId) {
        fetchData(mSymptomAPI.getSymptomList(userId), true);
    }

    @Override
    protected void onSuccess(SymptomEntity response) {
        mISymptomModel.onReceiveSymptomSuccess(response);
    }

    @Override
    protected void onFailed(Throwable e) {
        mISymptomModel.onReceiveFailed("获取不适症状失败，请重试！");
    }
}
