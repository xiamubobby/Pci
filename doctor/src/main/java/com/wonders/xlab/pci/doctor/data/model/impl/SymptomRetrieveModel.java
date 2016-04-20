package com.wonders.xlab.pci.doctor.data.model.impl;


import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.SymptomAPI;
import com.wonders.xlab.pci.doctor.data.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.data.model.ISymptomRetrieveModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomRetrieveModel extends DoctorBaseModel<SymptomEntity> implements ISymptomRetrieveModel {
    private SymptomRetrieveModelListener mListener;
    private SymptomAPI mSymptomAPI;

    public SymptomRetrieveModel(SymptomRetrieveModelListener listener) {
        mListener = listener;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    @Override
    protected void onSuccess(SymptomEntity response) {
        mListener.onReceiveSymptomListSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getSymptomList(String patientId, int page, int size) {
        request(mSymptomAPI.getSymptomList(patientId,page, size), true);
    }

    public interface SymptomRetrieveModelListener extends BaseModelListener {
        void onReceiveSymptomListSuccess(SymptomEntity.RetValuesEntity valuesEntity);
    }
}
