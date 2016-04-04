package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.module.main.home.dailyreport.otto.SymptomSaveSuccessOtto;
import com.wonders.xlab.patient.mvp.entity.SymptomEntity;
import com.wonders.xlab.patient.mvp.model.ISymptomDictModel;
import com.wonders.xlab.patient.mvp.model.ISymptomSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.SymptomDictModel;
import com.wonders.xlab.patient.mvp.model.impl.SymptomSaveModel;
import com.wonders.xlab.patient.mvp.presenter.ISymptomPresenter;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/3/16.
 */
public class SymptomRecordPresenter extends BasePresenter implements ISymptomPresenter,SymptomDictModel.SymptomDictModelListener, SymptomSaveModel.SymptomSaveModelListener {
    private SymptomPresenterListener mSymptomPresenterListener;

    private ISymptomDictModel mSymptomDictModel;

    private ISymptomSaveModel mSymptomSaveModel;

    public SymptomRecordPresenter(SymptomPresenterListener symptomPresenterListener) {
        mSymptomPresenterListener = symptomPresenterListener;

        mSymptomDictModel = new SymptomDictModel(this);
        mSymptomSaveModel = new SymptomSaveModel(this);
        addModel(mSymptomDictModel);
        addModel(mSymptomSaveModel);
    }

    @Override
    public void getSymptoms() {
        mSymptomDictModel.getSymptoms();
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
        /**
         * notify the daily report to refresh
         */
        OttoManager.post(new SymptomSaveSuccessOtto());

        mSymptomPresenterListener.hideLoading();
        mSymptomPresenterListener.onSaveSymptomSuccess(message);
    }

    public interface SymptomPresenterListener extends BasePresenterListener {
        void showSymptoms(SymptomEntity.RetValuesEntity valuesEntity);

        void onSaveSymptomSuccess(String message);
    }
}
