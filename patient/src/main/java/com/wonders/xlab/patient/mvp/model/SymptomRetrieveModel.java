package com.wonders.xlab.patient.mvp.model;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.SymptomAPI;
import com.wonders.xlab.patient.mvp.model.impl.ISymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

/**
 * Created by hua on 15/12/18.
 */
public class SymptomRetrieveModel extends PatientBaseModel<SymptomEntity> {

    private ISymptomRetrieveModel mISymptomRetrieveModel;
    private SymptomAPI mSymptomAPI;

    public SymptomRetrieveModel(ISymptomRetrieveModel iSymptomRetrieveModel) {
        mISymptomRetrieveModel = iSymptomRetrieveModel;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptoms() {
        fetchData(mSymptomAPI.getSymptoms(), true);
    }


    @Override
    protected void onSuccess(SymptomEntity response) {
        mISymptomRetrieveModel.onReceiveSymptomsSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e) {
        mISymptomRetrieveModel.onReceiveFailed("获取不适症状列表失败！");
    }

}
