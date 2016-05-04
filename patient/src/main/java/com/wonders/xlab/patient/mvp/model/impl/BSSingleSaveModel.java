package com.wonders.xlab.patient.mvp.model.impl;


import android.text.TextUtils;

import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.module.main.home.dailyreport.adapter.bean.BSReportBean;
import com.wonders.xlab.patient.mvp.api.BSAPI;
import com.wonders.xlab.patient.mvp.entity.BSSaveEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class BSSingleSaveModel extends PatientBaseModel<BSSaveEntity> {
    private BSSaveModelListener mBSSaveModelListener;
    private BSAPI mBSAPI;

    public BSSingleSaveModel(BSSaveModelListener BSSaveModelListener) {
        mBSSaveModelListener = BSSaveModelListener;
        mBSAPI = mRetrofit.create(BSAPI.class);
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
        request(mBSAPI.saveBSSingle(patientId, date, timeIndex, bloodSugarValue), true);
    }

    @Override
    protected void onSuccess(BSSaveEntity response) {
        BSSaveEntity.RetValuesEntity retValues = response.getRet_values();
        if (null != retValues) {
            XApplication.realm.beginTransaction();
            BSReportBean bean = XApplication.realm.createObject(BSReportBean.class);
            bean.setId(retValues.getId());
            bean.setMeasurePeriod(retValues.getBloodSugarTime());
            bean.setBloodSugar(retValues.getBloodSugar());
            bean.setContent(retValues.getContent());
            bean.setRecordTimeInMill(retValues.getRecordTime());
            bean.setBloodSugarStatus(retValues.getStatus());
            bean.setPatientId(retValues.getUserId());
            XApplication.realm.commitTransaction();
        }
        mBSSaveModelListener.onSaveSingleBSSuccess("保存血糖成功！");
    }

    @Override
    protected void onFailed(int code, String message) {
        if (TextUtils.isEmpty(message)) {
            mBSSaveModelListener.onReceiveFailed(code, "保存血糖失败，请重试！");
        } else {
            mBSSaveModelListener.onReceiveFailed(code, message);
        }

    }

    public interface BSSaveModelListener extends BaseModelListener {
        void onSaveSingleBSSuccess(String message);
    }
}
