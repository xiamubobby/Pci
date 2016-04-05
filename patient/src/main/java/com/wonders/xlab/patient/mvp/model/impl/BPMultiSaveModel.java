package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BPEntityList;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.module.main.home.dailyreport.adapter.bean.BPReportBean;
import com.wonders.xlab.patient.mvp.api.BPAPI;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class BPMultiSaveModel extends PatientBaseModel<SimpleEntity> {

    private BPReportBean mTmpReportBean = new BPReportBean();

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
        fetchData(mBPAPI.saveBP(userId, bpEntityList),true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        /**
         * save
         */
        XApplication.realm.beginTransaction();
        BPReportBean bean = XApplication.realm.copyToRealm(mTmpReportBean);
        XApplication.realm.commitTransaction();

        mBPSaveModelListener.onSaveMultiBPSuccess("保存血压成功！");
    }

    @Override
    protected void onFailed(String message) {
        mBPSaveModelListener.onReceiveFailed(message);
    }

    public interface BPMultiSaveModelListener extends BaseModelListener {
        void onSaveMultiBPSuccess(String message);
    }
}
