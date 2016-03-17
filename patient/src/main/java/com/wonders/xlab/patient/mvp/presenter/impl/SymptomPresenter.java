package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.mvp.entity.SymptomEntity;
import com.wonders.xlab.patient.mvp.model.impl.SymptomRetrieveModel;
import com.wonders.xlab.patient.mvp.model.impl.SymptomSaveModel;
import com.wonders.xlab.patient.mvp.presenter.ISymptomPresenter;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/3/16.
 */
public class SymptomPresenter extends BasePresenter implements ISymptomPresenter,SymptomRetrieveModel.SymptomRetrieveModelListener, SymptomSaveModel.SymptomSaveModelListener {
    private SymptomPresenterListener mSymptomPresenterListener;

    private SymptomRetrieveModel mSymptomRetrieveModel;

    private SymptomSaveModel mSymptomSaveModel;

    public SymptomPresenter(SymptomPresenterListener symptomPresenterListener) {
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

    public interface SymptomPresenterListener extends BasePresenterListener {
        void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity);

        void onSaveSymptomSuccess(String message);
    }
}
