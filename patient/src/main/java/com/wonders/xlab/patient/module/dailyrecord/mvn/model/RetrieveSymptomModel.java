package com.wonders.xlab.patient.module.dailyrecord.mvn.model;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.api.SymptomAPI;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl.IRetrieveSymptomModel;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

/**
 * Created by hua on 15/12/18.
 */
public class RetrieveSymptomModel extends PatientBaseModel<SymptomEntity> {

    private IRetrieveSymptomModel mIRetrieveSymptomModel;
    private SymptomAPI mSymptomAPI;

    public RetrieveSymptomModel(IRetrieveSymptomModel iRetrieveSymptomModel) {
        mIRetrieveSymptomModel = iRetrieveSymptomModel;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    public void getSymptoms() {
        fetchData(mSymptomAPI.getSymptoms(), true);
    }


    @Override
    protected void onSuccess(SymptomEntity response) {
        mIRetrieveSymptomModel.onReceiveSymptomsSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e) {
        mIRetrieveSymptomModel.onReceiveFailed("获取不适症状列表失败！");
    }

}
