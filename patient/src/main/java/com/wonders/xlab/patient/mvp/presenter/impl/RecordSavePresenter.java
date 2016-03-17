package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.mvp.model.impl.BPSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.BSSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.SymptomSaveModel;
import com.wonders.xlab.patient.mvp.model.listener.BPSaveModelListener;
import com.wonders.xlab.patient.mvp.model.listener.BSSaveModelListener;
import com.wonders.xlab.patient.mvp.model.listener.SymptomSaveModelListener;
import com.wonders.xlab.patient.mvp.presenter.IRecordSavePresenter;
import com.wonders.xlab.patient.mvp.presenter.listener.RecordAddPresenterListener;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public class RecordSavePresenter extends BasePresenter implements IRecordSavePresenter, BPSaveModelListener, SymptomSaveModelListener, BSSaveModelListener {
    private RecordAddPresenterListener mRecordAddPresenterListener;

    private BPSaveModel mBPSaveModel;
    private BSSaveModel mBSSaveModel;
    private SymptomSaveModel mSymptomSaveModel;

    public RecordSavePresenter(RecordAddPresenterListener RecordAddPresenterListener) {
        mRecordAddPresenterListener = RecordAddPresenterListener;
        mBPSaveModel = new BPSaveModel(this);
        mBSSaveModel = new BSSaveModel(this);
        mSymptomSaveModel = new SymptomSaveModel(this);

        addModel(mBPSaveModel);
        addModel(mBSSaveModel);
        addModel(mSymptomSaveModel);
    }

    @Override
    public void saveBSSingle(String userId, long date, int timeIndex, float bloodSugarValue) {
        mBSSaveModel.saveBSSingle(userId, date, timeIndex, bloodSugarValue);

    }

    @Override
    public void saveBS(String userId, BSEntityList bsEntityList) {
        mBSSaveModel.saveBS(userId, bsEntityList);
    }

    @Override
    public void saveBP(String userId, BPEntityList bpEntityList) {
        mBPSaveModel.saveBP(userId, bpEntityList);
    }

    @Override
    public void saveBPSingle(String userId, long date, int heartRate, int systolicPressure, int diastolicPressure) {
        mBPSaveModel.saveBPSingle(userId, date, heartRate, systolicPressure, diastolicPressure);
    }

    @Override
    public void onSaveBPSuccess(String message) {
        mRecordAddPresenterListener.hideLoading();
        mRecordAddPresenterListener.onSaveRecordSuccess(message);
    }

    @Override
    public void onSaveBSSuccess(String message) {
        mRecordAddPresenterListener.hideLoading();
        mRecordAddPresenterListener.onSaveRecordSuccess(message);
    }

    @Override
    public void onSaveSymptomSuccess(String message) {
        mRecordAddPresenterListener.hideLoading();
        mRecordAddPresenterListener.onSaveRecordSuccess(message);
    }

    @Override
    public void onReceiveFailed(String message) {
        mRecordAddPresenterListener.hideLoading();
        mRecordAddPresenterListener.showError(message);
    }
}
