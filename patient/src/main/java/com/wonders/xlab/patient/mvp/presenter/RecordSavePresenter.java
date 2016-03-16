package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.mvp.model.BPSaveModel;
import com.wonders.xlab.patient.mvp.model.BSSaveModel;
import com.wonders.xlab.patient.mvp.model.SymptomSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.IBPSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.IBSSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.ISymptomSaveModel;
import com.wonders.xlab.patient.mvp.presenter.impl.IRecordAddPresenter;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public class RecordSavePresenter extends BasePresenter implements IBPSaveModel, ISymptomSaveModel, IBSSaveModel {
    private IRecordAddPresenter mIRecordAddPresenter;

    private BPSaveModel mBPSaveModel;
    private BSSaveModel mBSSaveModel;
    private SymptomSaveModel mSymptomSaveModel;

    public RecordSavePresenter(IRecordAddPresenter IRecordAddPresenter) {
        mIRecordAddPresenter = IRecordAddPresenter;
        mBPSaveModel = new BPSaveModel(this);
        mBSSaveModel = new BSSaveModel(this);
        mSymptomSaveModel = new SymptomSaveModel(this);

        addModel(mBPSaveModel);
        addModel(mBSSaveModel);
        addModel(mSymptomSaveModel);
    }

    /**
     * 保存一条血糖数据
     *
     * @param userId
     * @param date
     * @param timeIndex
     * @param bloodSugarValue
     */
    public void saveBSSingle(String userId, long date, int timeIndex, float bloodSugarValue) {
        mBSSaveModel.saveBSSingle(userId,date,timeIndex,bloodSugarValue);

    }

    /**
     * 批量保存血糖数据
     *
     * @param userId
     * @param bsEntityList
     */
    public void saveBS(String userId, BSEntityList bsEntityList) {
        mBSSaveModel.saveBS(userId,bsEntityList);
    }

    /**
     * 批量保存血压数据
     *
     * @param userId
     * @param bpEntityList
     */
    public void saveBP(String userId, BPEntityList bpEntityList) {
        mBPSaveModel.saveBP(userId,bpEntityList);
    }

    /**
     * 保存一条血压数据
     *
     * @param userId
     * @param date
     * @param heartRate
     * @param systolicPressure
     * @param diastolicPressure
     */
    public void saveBPSingle(String userId, long date, int heartRate, int systolicPressure, int diastolicPressure) {
        mBPSaveModel.saveBPSingle(userId,date,heartRate,systolicPressure,diastolicPressure);
    }

    @Override
    public void onSaveBPSuccess(String message) {
        mIRecordAddPresenter.hideLoading();
        mIRecordAddPresenter.onSaveRecordSuccess(message);
    }

    @Override
    public void onSaveBSSuccess(String message) {
        mIRecordAddPresenter.hideLoading();
        mIRecordAddPresenter.onSaveRecordSuccess(message);
    }

    @Override
    public void onSaveSymptomSuccess(String message) {
        mIRecordAddPresenter.hideLoading();
        mIRecordAddPresenter.onSaveRecordSuccess(message);
    }

    @Override
    public void onReceiveFailed(String message) {
        mIRecordAddPresenter.hideLoading();
        mIRecordAddPresenter.showError(message);
    }
}
