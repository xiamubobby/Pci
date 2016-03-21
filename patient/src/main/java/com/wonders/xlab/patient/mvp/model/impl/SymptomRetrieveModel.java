package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.SymptomAPI;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomRetrieveModel extends PatientBaseModel<SymptomEntity> {

    private SymptomRetrieveModelListener mSymptomRetrieveModelListener;
    private SymptomAPI mSymptomAPI;

    public SymptomRetrieveModel(SymptomRetrieveModelListener symptomRetrieveModelListener) {
        mSymptomRetrieveModelListener = symptomRetrieveModelListener;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptoms() {
        fetchData(mSymptomAPI.getSymptoms(), true);
    }


    @Override
    protected void onSuccess(SymptomEntity response) {
        mSymptomRetrieveModelListener.onReceiveSymptomsSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        mSymptomRetrieveModelListener.onReceiveFailed("获取不适症状列表失败！");
    }

    public interface SymptomRetrieveModelListener extends BaseModelListener {
        void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity);
    }
}
