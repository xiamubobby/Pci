package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.SymptomAPI;
import com.wonders.xlab.pci.doctor.data.entity.SymptomEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

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
        request(mSymptomAPI.getSymptomList(userId), true);
    }

    @Override
    protected void onSuccess(SymptomEntity response) {
        mISymptomModel.onReceiveSymptomSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mISymptomModel.onReceiveFailed(code, message);
    }

    public interface SymptomModelListener extends BaseModelListener {
        void onReceiveSymptomSuccess(SymptomEntity entity);
    }
}
