package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.module.dailyreport.otto.BPSaveSuccessOtto;
import com.wonders.xlab.patient.mvp.model.impl.BPMultiSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.BPSingleSaveModel;
import com.wonders.xlab.patient.mvp.presenter.IBPSavePresenter;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/3/16.
 */
public class BPSavePresenter extends BasePresenter implements IBPSavePresenter,BPMultiSaveModel.BPMultiSaveModelListener, BPSingleSaveModel.BPSingleSaveModelListener {

    private RecordSavePresenterListener mRecordSavePresenterListener;

    private BPSingleSaveModel mBPSingleSaveModel;
    private BPMultiSaveModel mBPMultiSaveModel;

    public BPSavePresenter(RecordSavePresenterListener RecordSavePresenterListener) {
        mRecordSavePresenterListener = RecordSavePresenterListener;
        mBPMultiSaveModel = new BPMultiSaveModel(this);
        mBPSingleSaveModel = new BPSingleSaveModel(this);

        addModel(mBPMultiSaveModel);
        addModel(mBPSingleSaveModel);
    }

    @Override
    public void saveBP(String userId, BPEntityList bpEntityList) {
        mBPMultiSaveModel.saveBP(userId, bpEntityList);
    }

    @Override
    public void saveBPSingle(String patientId, long date, int heartRate, int systolicPressure, int diastolicPressure) {

        mBPSingleSaveModel.saveBPSingle(patientId, date, heartRate, systolicPressure, diastolicPressure);
    }

    @Override
    public void onSaveSingleBPSuccess(String message) {
        /**
         * notify
         */
        OttoManager.post(new BPSaveSuccessOtto());

        mRecordSavePresenterListener.hideLoading();
        mRecordSavePresenterListener.onSaveBPSuccess(message);
    }

    @Override
    public void onReceiveFailed(String message) {
        mRecordSavePresenterListener.hideLoading();
        mRecordSavePresenterListener.showError(message);
    }

    @Override
    public void onSaveMultiBPSuccess(String message) {
        mRecordSavePresenterListener.hideLoading();
        mRecordSavePresenterListener.onSaveBPSuccess(message);
    }

    public interface RecordSavePresenterListener extends BasePresenterListener {
        void onSaveBPSuccess(String message);
    }
}
