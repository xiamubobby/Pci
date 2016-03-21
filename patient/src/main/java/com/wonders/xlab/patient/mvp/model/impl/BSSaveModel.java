package com.wonders.xlab.patient.mvp.model.impl;


import android.text.TextUtils;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BSReportBean;
import com.wonders.xlab.patient.mvp.api.AddRecordAPI;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class BSSaveModel extends PatientBaseModel<SimpleEntity> {
    private BSReportBean mTmpReportBean = new BSReportBean();

    private BSSaveModelListener mBSSaveModelListener;
    private AddRecordAPI mAddRecordAPI;

    public BSSaveModel(BSSaveModelListener BSSaveModelListener) {
        mBSSaveModelListener = BSSaveModelListener;
        mAddRecordAPI = mRetrofit.create(AddRecordAPI.class);
    }

    /**
     * 保存一条血糖数据
     *
     * @param patientId
     * @param date
     * @param timeIndex
     * @param bloodSugarValue
     */
    public void saveBSSingle(String patientId, long date, int timeIndex, float bloodSugarValue) {
        /**
         * cache the bp bean
         */
        mTmpReportBean.setPatientId(patientId);
        mTmpReportBean.setId(String.valueOf(date));
        mTmpReportBean.setBloodSugar(String.valueOf(bloodSugarValue));
        mTmpReportBean.setMeasurePeriod(String.valueOf(timeIndex));
        mTmpReportBean.setRecordTimeInMill(date);

        fetchData(mAddRecordAPI.saveBSSingle(patientId, date, timeIndex, bloodSugarValue), true);
    }

    /**
     * 批量保存血糖数据
     *
     * @param userId
     * @param bsEntityList
     */
    public void saveBS(String userId, BSEntityList bsEntityList) {
        fetchData(mAddRecordAPI.saveBS(userId, bsEntityList), true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        XApplication.realm.beginTransaction();
        BSReportBean bean = XApplication.realm.copyToRealm(mTmpReportBean);
        XApplication.realm.commitTransaction();

        mBSSaveModelListener.onSaveBSSuccess("保存血糖成功！");
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        if (TextUtils.isEmpty(message)) {
            mBSSaveModelListener.onReceiveFailed("保存血糖失败，请重试！");
        } else {
            mBSSaveModelListener.onReceiveFailed(message);
        }

    }

    public interface BSSaveModelListener extends BaseModelListener {
        void onSaveBSSuccess(String message);
    }
}
