package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.BSAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.BSEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IBSModel;

/**
 * Created by hua on 16/2/22.
 */
public class BSModel extends DoctorBaseModel<BSEntity> {
    private BSAPI mBSAPI;
    private IBSModel mBloodPressureModel;

    public BSModel(IBSModel bloodPressureModel) {
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
    protected void onFailed(Throwable e) {
        mBloodPressureModel.onReceiveFailed("获取血糖数据失败，请重试！");
    }
}
