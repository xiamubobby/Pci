package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AddRecordAPI;
import com.wonders.xlab.patient.mvp.model.listener.BPSaveModelListener;

import im.hua.library.base.mvp.entity.SimpleEntity;

/**
 * Created by hua on 15/12/18.
 */
public class BPSaveModel extends PatientBaseModel<SimpleEntity> {
    private BPSaveModelListener mBPSaveModelListener;
    private AddRecordAPI mAddRecordAPI;

    public BPSaveModel(BPSaveModelListener BPSaveModelListener) {
        mBPSaveModelListener = BPSaveModelListener;
        mAddRecordAPI = mRetrofit.create(AddRecordAPI.class);
    }

    /**
     * 批量保存血压数据
     *
     * @param userId
     * @param bpEntityList
     */
    public void saveBP(String userId, BPEntityList bpEntityList) {
        fetchData(mAddRecordAPI.saveBP(userId, bpEntityList),true);
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
        fetchData(mAddRecordAPI.saveBPSingle(userId, date, heartRate, systolicPressure, diastolicPressure),true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mBPSaveModelListener.onSaveBPSuccess("保存血压成功！");
    }

    @Override
    protected void onFailed(Throwable e) {
        mBPSaveModelListener.onReceiveFailed("保存血压失败，请重试！");
    }
}
