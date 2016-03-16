package com.wonders.xlab.patient.module.dailyrecord.mvn.presenter;

import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.SaveBPModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.SaveBSModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.SaveSymptomModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl.ISaveBPModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl.ISaveBSModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl.ISaveSymptomModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.impl.IAddRecordPresenter;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public class AddRecordPresenter extends BasePresenter implements ISaveBPModel, ISaveSymptomModel, ISaveBSModel {
    private IAddRecordPresenter mIAddRecordPresenter;

    private SaveBPModel mSaveBPModel;
    private SaveBSModel mSaveBSModel;
    private SaveSymptomModel mSaveSymptomModel;

    public AddRecordPresenter(IAddRecordPresenter IAddRecordPresenter) {
        mIAddRecordPresenter = IAddRecordPresenter;
        mSaveBPModel = new SaveBPModel(this);
        mSaveBSModel = new SaveBSModel(this);
        mSaveSymptomModel = new SaveSymptomModel(this);

        addModel(mSaveBPModel);
        addModel(mSaveBSModel);
        addModel(mSaveSymptomModel);
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
        mSaveBSModel.saveBSSingle(userId,date,timeIndex,bloodSugarValue);

    }

    /**
     * 批量保存血糖数据
     *
     * @param userId
     * @param bsEntityList
     */
    public void saveBS(String userId, BSEntityList bsEntityList) {
        mSaveBSModel.saveBS(userId,bsEntityList);
    }

    /**
     * 批量保存血压数据
     *
     * @param userId
     * @param bpEntityList
     */
    public void saveBP(String userId, BPEntityList bpEntityList) {
        mSaveBPModel.saveBP(userId,bpEntityList);
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
        mSaveBPModel.saveBPSingle(userId,date,heartRate,systolicPressure,diastolicPressure);
    }

    /**
     * 保存不适症状
     *
     * @param userId
     * @param symptomIdsStr
     */
    public void saveSymptom(String userId, String[] symptomIdsStr) {
        mSaveSymptomModel.saveSymptom(userId,symptomIdsStr);
    }

    @Override
    public void onSaveBPSuccess(String message) {
        mIAddRecordPresenter.hideLoading();
        mIAddRecordPresenter.onSaveRecordSuccess(message);
    }

    @Override
    public void onSaveBSSuccess(String message) {
        mIAddRecordPresenter.hideLoading();
        mIAddRecordPresenter.onSaveRecordSuccess(message);
    }

    @Override
    public void onSaveSymptomSuccess(String message) {
        mIAddRecordPresenter.hideLoading();
        mIAddRecordPresenter.onSaveRecordSuccess(message);
    }

    @Override
    public void onReceiveFailed(String message) {
        mIAddRecordPresenter.hideLoading();
        mIAddRecordPresenter.showError(message);
    }
}
