package com.wonders.xlab.patient.mvp.model.impl;


import android.text.TextUtils;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BPReportBean;
import com.wonders.xlab.patient.mvp.api.AddRecordAPI;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class BPSaveModel extends PatientBaseModel<SimpleEntity> {

    private BPReportBean mTmpReportBean = new BPReportBean();

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
     * @param patientId
     * @param date
     * @param heartRate
     * @param systolicPressure
     * @param diastolicPressure
     */
    public void saveBPSingle(String patientId, long date, int heartRate, int systolicPressure, int diastolicPressure) {
        /**
         * cache the bp bean
         */
        mTmpReportBean.setPatientId(patientId);
        mTmpReportBean.setId(String.valueOf(date));
        mTmpReportBean.setLowPressure(String.valueOf(diastolicPressure));
        mTmpReportBean.setHighPressure(String.valueOf(systolicPressure));
        mTmpReportBean.setHeartRate(String.valueOf(heartRate));
        mTmpReportBean.setRecordTimeInMill(date);

        fetchData(mAddRecordAPI.saveBPSingle(patientId, date, heartRate, systolicPressure, diastolicPressure),true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        /**
         * save
         */
        XApplication.realm.beginTransaction();
        BPReportBean bean = XApplication.realm.copyToRealm(mTmpReportBean);
        XApplication.realm.commitTransaction();

        mBPSaveModelListener.onSaveBPSuccess("保存血压成功！");
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        if (TextUtils.isEmpty(message)) {
            mBPSaveModelListener.onReceiveFailed("保存血压失败，请重试！");
        } else {
            mBPSaveModelListener.onReceiveFailed(message);
        }
    }

    public interface BPSaveModelListener extends BaseModelListener {
        void onSaveBPSuccess(String message);
    }
}
