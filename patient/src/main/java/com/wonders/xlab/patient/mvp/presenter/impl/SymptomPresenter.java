package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.mvp.entity.SymptomEntity;
import com.wonders.xlab.patient.mvp.model.impl.SymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.model.impl.SymptomSaveModel;
import com.wonders.xlab.patient.mvp.model.listener.SymptomRetrieveModelListener;
import com.wonders.xlab.patient.mvp.model.listener.SymptomSaveModelListener;
import com.wonders.xlab.patient.mvp.presenter.ISymptomPresenter;

import im.hua.library.base.mvp.impl.BasePresenter;


/**
 * Created by hua on 16/3/16.
 */
public class SymptomPresenter extends BasePresenter implements ISymptomPresenter,SymptomRetrieveModelListener, SymptomSaveModelListener {
    private com.wonders.xlab.patient.mvp.presenter.listener.SymptomPresenterListener mSymptomPresenterListener;

    private SymptomRetrieveModel mSymptomRetrieveModel;

    private SymptomSaveModel mSymptomSaveModel;

    public SymptomPresenter(com.wonders.xlab.patient.mvp.presenter.listener.SymptomPresenterListener symptomPresenterListener) {
        mSymptomPresenterListener = symptomPresenterListener;

        mSymptomRetrieveModel = new SymptomRetrieveModel(this);
        mSymptomSaveModel = new SymptomSaveModel(this);
        addModel(mSymptomRetrieveModel);
        addModel(mSymptomSaveModel);
    }

    @Override
    public void getSymptoms() {
        mSymptomRetrieveModel.getSymptoms();
    }

    @Override
    public void saveSymptom(String patientId, String[] symptomIdsStr) {
        mSymptomSaveModel.saveSymptom(patientId,symptomIdsStr);
    }

    @Override
    public void onReceiveSymptomsSuccess(SymptomEntity.RetValuesEntity valuesEntity) {
        mSymptomPresenterListener.hideLoading();
        mSymptomPresenterListener.showSymptoms(valuesEntity);
    }

    @Override
    public void onReceiveFailed(String message) {
        mSymptomPresenterListener.hideLoading();
        mSymptomPresenterListener.showError(message);
    }

    @Override
    public void onSaveSymptomSuccess(String message) {
        mSymptomPresenterListener.hideLoading();
        mSymptomPresenterListener.onSaveSymptomSuccess(message);
    }
}
