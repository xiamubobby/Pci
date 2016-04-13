package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.BSAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.BSEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/22.
 */
public class BSModel extends DoctorBaseModel<BSEntity> {
    private BSAPI mBSAPI;
    private BSModelListener mBloodPressureModel;

    public BSModel(BSModelListener bloodPressureModel) {
        mBloodPressureModel = bloodPressureModel;
        mBSAPI = mRetrofit.create(BSAPI.class);
    }

    public void getBSList(String userId) {
        fetchData(mBSAPI.getBSList(userId), true);
    }

    @Override
    protected void onSuccess(BSEntity response) {
        mBloodPressureModel.onReceiveBSSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mBloodPressureModel.onReceiveFailed(code, "获取血糖数据失败，请重试！");
    }

    public interface BSModelListener extends BaseModelListener {
        void onReceiveBSSuccess(BSEntity bsEntity);
    }
}
