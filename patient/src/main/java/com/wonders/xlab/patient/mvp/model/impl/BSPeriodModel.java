package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.BSPeriodAPI;
import com.wonders.xlab.patient.mvp.entity.BSPeriodEntity;
import com.wonders.xlab.patient.mvp.model.IBSPeriodModel;

import java.util.Calendar;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/21.
 */
public class BSPeriodModel extends PatientBaseModel<BSPeriodEntity> implements IBSPeriodModel {
    private BSPeriodAPI mPeriodAPI;
    private BSPeriodModelListener mBSPeriodModelListener;

    public BSPeriodModel(BSPeriodModelListener bsPeriodModelListener) {
        mBSPeriodModelListener = bsPeriodModelListener;

        mPeriodAPI = mRetrofit.create(BSPeriodAPI.class);
    }

    @Override
    protected void onSuccess(BSPeriodEntity response) {
        mBSPeriodModelListener.onReceiveBSPeriodSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mBSPeriodModelListener.onReceiveFailed(code, message);
    }

    @Override
    public void getPeriodDic() {
        fetchData(mPeriodAPI.getPeriodDic(Calendar.getInstance().getTimeInMillis()), true);
    }

    public interface BSPeriodModelListener extends BaseModelListener {
        void onReceiveBSPeriodSuccess(BSPeriodEntity.RetValuesEntity valuesEntity);
    }

}
