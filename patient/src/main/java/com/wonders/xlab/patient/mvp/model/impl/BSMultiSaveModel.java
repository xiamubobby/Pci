package com.wonders.xlab.patient.mvp.model.impl;


import android.text.TextUtils;

import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BSReportBean;
import com.wonders.xlab.patient.mvp.api.BSAPI;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 15/12/18.
 */
public class BSMultiSaveModel extends PatientBaseModel<SimpleEntity> {
    private BSReportBean mTmpReportBean = new BSReportBean();

    private BSSaveModelListener mBSSaveModelListener;
    private BSAPI mBSAPI;

    public BSMultiSaveModel(BSSaveModelListener BSSaveModelListener) {
        mBSSaveModelListener = BSSaveModelListener;
        mBSAPI = mRetrofit.create(BSAPI.class);
    }

    /**
     * 批量保存血糖数据
     *
     * @param userId
     * @param bsEntityList
     */
    public void saveBS(String userId, BSEntityList bsEntityList) {
        request(mBSAPI.saveBS(userId, bsEntityList), true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mBSSaveModelListener.onSaveMultiBSSuccess("保存血糖成功！");
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
        void onSaveMultiBSSuccess(String message);
    }
}
