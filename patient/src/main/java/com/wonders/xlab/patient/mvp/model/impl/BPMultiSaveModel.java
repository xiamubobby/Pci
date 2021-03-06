package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportRealmBean;
import com.wonders.xlab.patient.mvp.api.BPAPI;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class BPMultiSaveModel extends PatientBaseModel<SimpleEntity> {

    private BPReportRealmBean mTmpReportBean = new BPReportRealmBean();

    private BPMultiSaveModelListener mBPSaveModelListener;
    private BPAPI mBPAPI;

    public BPMultiSaveModel(BPMultiSaveModelListener BPSaveModelListener) {
        mBPSaveModelListener = BPSaveModelListener;
        mBPAPI = mRetrofit.create(BPAPI.class);
    }

    /**
     * 批量保存血压数据
     *
     * @param userId
     * @param bpEntityList
     */
    public void saveBP(String userId, BPEntityList bpEntityList) {
        request(mBPAPI.saveBP(userId, bpEntityList),true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        /**
         * save
         */
        XApplication.realm.beginTransaction();
        BPReportRealmBean bean = XApplication.realm.copyToRealm(mTmpReportBean);
        XApplication.realm.commitTransaction();

        mBPSaveModelListener.onSaveMultiBPSuccess("保存血压成功！");
    }

    @Override
    protected void onFailed(int code, String message) {
        mBPSaveModelListener.onReceiveFailed(code, message);
    }

    public interface BPMultiSaveModelListener extends BaseModelListener {
        void onSaveMultiBPSuccess(String message);
    }
}
