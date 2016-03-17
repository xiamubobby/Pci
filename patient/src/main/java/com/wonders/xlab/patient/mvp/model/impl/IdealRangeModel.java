package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.IdealRangeAPI;

import im.hua.library.base.mvp.entity.SimpleEntity;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/1/6.
 */
public class IdealRangeModel extends PatientBaseModel<SimpleEntity> {
    private IdealRangeAPI mIdealRangeAPI;

    private IdealRangeModelListener mIdealRangeModelListener;


    public IdealRangeModel(IdealRangeModelListener idealRangeModel) {
        mIdealRangeModelListener = idealRangeModel;
        mIdealRangeAPI = mRetrofit.create(IdealRangeAPI.class);

    }

    /**
     * 获取血压理想范围
     *
     * @param userId
     */
    public void fetchIdealBPRange(String userId) {
        fetchData(mIdealRangeAPI.fetchIdealBPRange(userId), true);
    }

    /**
     * 获取血糖理想范围
     *
     * @param userId
     */
    public void fetchIdealBSRange(String userId) {
        fetchData(mIdealRangeAPI.fetchIdealBSRange(userId), true);
    }

    @Override
    protected void onSuccess(SimpleEntity response) {
        mIdealRangeModelListener.onReceiveIdealRangeSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e) {
        mIdealRangeModelListener.onReceiveFailed("获取理想范围失败！");
    }

    public interface IdealRangeModelListener extends BaseModelListener {
        void onReceiveIdealRangeSuccess(String message);
    }
}