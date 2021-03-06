package com.wonders.xlab.patient.module.dailyreport.datarecord.bp;


import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportRealmBean;
import com.wonders.xlab.patient.mvp.api.BPAPI;
import com.wonders.xlab.patient.mvp.entity.BPSaveEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class BPSingleSaveModel extends PatientBaseModel<BPSaveEntity> {

    private BPSingleSaveModelListener mBPSaveModelListener;
    private BPAPI mBPAPI;

    public BPSingleSaveModel(BPSingleSaveModelListener BPSaveModelListener) {
        mBPSaveModelListener = BPSaveModelListener;
        mBPAPI = mRetrofit.create(BPAPI.class);
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
        request(mBPAPI.saveBPSingle(patientId, date, heartRate, systolicPressure, diastolicPressure), true);
    }

    @Override
    protected void onSuccess(BPSaveEntity response) {
        BPSaveEntity.RetValuesEntity retValues = response.getRet_values();
        /**
         * cache to realm
         */
        if (null != retValues) {

            XApplication.realm.beginTransaction();
            BPReportRealmBean bean = XApplication.realm.createObject(BPReportRealmBean.class);
            bean.setLowPressure(retValues.getDiastolicPressure());
            bean.setLowPressureStatus(retValues.getDiastolicStatus());
            bean.setLowPressureRange(retValues.getDiastolicContent());
            bean.setHighPressure(retValues.getSystolicPressure());
            bean.setHighPressureStatus(retValues.getSystolicStatus());
            bean.setHighPressureRange(retValues.getSystolicContent());
            bean.setHeartRate(retValues.getHeartRate());
            bean.setHeartRateStatus(retValues.getHeartStatus());
            bean.setHeartRateRange(retValues.getHeartContent());
            bean.setId(retValues.getId());
            bean.setPatientId(retValues.getUserId());
            bean.setRecordTimeInMill(retValues.getRecordTime());
            XApplication.realm.commitTransaction();
        }

        mBPSaveModelListener.onSaveSingleBPSuccess("保存血压成功！");
    }

    @Override
    protected void onFailed(int code, String message) {
        mBPSaveModelListener.onReceiveFailed(code, message);
    }

    public interface BPSingleSaveModelListener extends BaseModelListener {
        void onSaveSingleBPSuccess(String message);
    }
}
