package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.model.SymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.model.SymptomSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.ISymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.model.impl.ISymptomSaveModel;
import com.wonders.xlab.patient.mvp.presenter.impl.ISymptomPresenter;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;

import im.hua.library.base.mvp.BasePresenter;


/**
 * Created by hua on 16/3/16.
 */
public class SymptomPresenter extends BasePresenter implements ISymptomRetrieveModel, ISymptomSaveModel {
    private ISymptomPresenter mISymptomPresenter;

    private SymptomRetrieveModel mSymptomRetrieveModel;

    private SymptomSaveModel mSymptomSaveModel;

    public SymptomPresenter(ISymptomPresenter iSymptomPresenter) {
        mISymptomPresenter = iSymptomPresenter;

        mSymptomRetrieveModel = new SymptomRetrieveModel(this);
        mSymptomSaveModel = new SymptomSaveModel(this);
        addModel(mSymptomRetrieveModel);
        addModel(mSymptomSaveModel);
    }

    public void getSymptoms() {
        mSymptomRetrieveModel.getSymptoms();
    }

    /**
     * 保存不适症状
     *
     * @param userId
     * @param symptomIdsStr
     */
    public void saveSymptom(String userId, String[] symptomIdsStr) {
        mSymptomSaveModel.saveSymptom(userId,symptomIdsStr);
    }

    @Override
    public void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity) {
        mISymptomPresenter.hideLoading();
        mISymptomPresenter.showSymptoms(valuesEntity);
    }

    @Override
    public void onReceiveFailed(String message) {
        mISymptomPresenter.hideLoading();
        mISymptomPresenter.showError(message);
    }

    @Override
    public void onSaveSymptomSuccess(String message) {
        mISymptomPresenter.hideLoading();
        mISymptomPresenter.onSaveSymptomSuccess(message);
    }
}
