package com.wonders.xlab.patient.module.dailyrecord.mvn.presenter;

import com.wonders.xlab.patient.module.dailyrecord.mvn.model.RetrieveSymptomModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl.IRetrieveSymptomModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.impl.IRetrieveSymptomPresenter;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public class RetrieveSymptomPresenter extends BasePresenter implements IRetrieveSymptomModel {
    private IRetrieveSymptomPresenter mIRetrieveSymptomPresenter;

    private RetrieveSymptomModel mRetrieveSymptomModel;

    public RetrieveSymptomPresenter(IRetrieveSymptomPresenter iRetrieveSymptomPresenter) {
        mIRetrieveSymptomPresenter = iRetrieveSymptomPresenter;

        mRetrieveSymptomModel = new RetrieveSymptomModel(this);
        addModel(mRetrieveSymptomModel);
    }

    public void getSymptoms() {
        mRetrieveSymptomModel.getSymptoms();
    }

    @Override
    public void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity) {
        mIRetrieveSymptomPresenter.showSymptoms(valuesEntity);
    }

    @Override
    public void onReceiveFailed(String message) {
        mIRetrieveSymptomPresenter.showError(message);
    }
}
