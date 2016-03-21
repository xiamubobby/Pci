package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.module.healthreport.otto.BPSaveSuccessOtto;
import com.wonders.xlab.patient.module.healthreport.otto.BSSaveSuccessOtto;
import com.wonders.xlab.patient.module.healthreport.otto.SymptomSaveSuccessOtto;
import com.wonders.xlab.patient.mvp.entity.BSPeriodEntity;
import com.wonders.xlab.patient.mvp.model.IBSPeriodModel;
import com.wonders.xlab.patient.mvp.model.impl.BPSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.BSPeriodModel;
import com.wonders.xlab.patient.mvp.model.impl.BSSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.SymptomSaveModel;
import com.wonders.xlab.patient.mvp.presenter.IRecordSavePresenter;

import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/3/16.
 */
public class RecordSavePresenter extends BasePresenter implements IRecordSavePresenter, BPSaveModel.BPSaveModelListener, SymptomSaveModel.SymptomSaveModelListener, BSSaveModel.BSSaveModelListener, BSPeriodModel.BSPeriodModelListener {

    private RecordSavePresenterListener mRecordSavePresenterListener;

    private BPSaveModel mBPSaveModel;
    private BSSaveModel mBSSaveModel;
    private SymptomSaveModel mSymptomSaveModel;
    private IBSPeriodModel mIBSPeriodModel;

    public RecordSavePresenter(RecordSavePresenterListener RecordSavePresenterListener) {
        mRecordSavePresenterListener = RecordSavePresenterListener;
        mBPSaveModel = new BPSaveModel(this);
        mBSSaveModel = new BSSaveModel(this);
        mIBSPeriodModel = new BSPeriodModel(this);
        mSymptomSaveModel = new SymptomSaveModel(this);

        addModel(mBPSaveModel);
        addModel(mBSSaveModel);
        addModel(mSymptomSaveModel);
        addModel(mIBSPeriodModel);
    }

    @Override
    public void getBSPeriodDic() {
        mIBSPeriodModel.getPeriodDic();
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
    public void saveBPSingle(String patientId, long date, int heartRate, int systolicPressure, int diastolicPressure) {

        mBPSaveModel.saveBPSingle(patientId, date, heartRate, systolicPressure, diastolicPressure);
    }

    @Override
    public void onSaveBPSuccess(String message) {
        /**
         * notify
         */
        OttoManager.post(new BPSaveSuccessOtto());

        mRecordSavePresenterListener.hideLoading();
        mRecordSavePresenterListener.onSaveRecordSuccess(message);
    }

    @Override
    public void onSaveBSSuccess(String message) {
        OttoManager.post(new BSSaveSuccessOtto());

        mRecordSavePresenterListener.hideLoading();
        mRecordSavePresenterListener.onSaveRecordSuccess(message);
    }

    @Override
    public void onSaveSymptomSuccess(String message) {
        OttoManager.post(new SymptomSaveSuccessOtto());

        mRecordSavePresenterListener.hideLoading();
        mRecordSavePresenterListener.onSaveRecordSuccess(message);
    }

    @Override
    public void onReceiveFailed(String message) {
        mRecordSavePresenterListener.hideLoading();
        mRecordSavePresenterListener.showError(message);
    }

    @Override
    public void onReceiveBSPeriodSuccess(BSPeriodEntity.RetValuesEntity valuesEntity) {

        mRecordSavePresenterListener.showBSPeriodDicList(valuesEntity.getTimeString(), valuesEntity.getCurrentTime());
    }

    public interface RecordSavePresenterListener extends BasePresenterListener {
        void onSaveRecordSuccess(String message);

        void showBSPeriodDicList(List<String> periodList, int currentPeriodIndex);
    }
}
