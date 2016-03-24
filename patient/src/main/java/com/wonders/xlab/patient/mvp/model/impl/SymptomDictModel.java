package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.SymptomAPI;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;
import com.wonders.xlab.patient.mvp.model.ISymptomDictModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 * 不适症状字典表
 */
public class SymptomDictModel extends PatientBaseModel<SymptomEntity> implements ISymptomDictModel {

    private SymptomDictModelListener mSymptomDictModelListener;
    private SymptomAPI mSymptomAPI;

    public SymptomDictModel(SymptomDictModelListener symptomDictModelListener) {
        mSymptomDictModelListener = symptomDictModelListener;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptoms() {
        fetchData(mSymptomAPI.getSymptomDicList(), true);
    }


    @Override
    protected void onSuccess(SymptomEntity response) {
        mSymptomDictModelListener.onReceiveSymptomsSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        mSymptomDictModelListener.onReceiveFailed("获取不适症状列表失败！");
    }

    public interface SymptomDictModelListener extends BaseModelListener {
        void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity);
    }
}
