package com.wonders.xlab.patient.mvp.model.impl;

import android.text.TextUtils;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.SymptomAPI;
import com.wonders.xlab.patient.mvp.entity.SymptomRetrieveEntity;
import com.wonders.xlab.patient.mvp.model.ISymptomRetrieveModel;

import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomRetrieveModel extends PatientBaseModel<SymptomRetrieveEntity> implements ISymptomRetrieveModel{
    private SymptomRetrieveModelListener mListener;
    private SymptomAPI mSymptomAPI;

    public SymptomRetrieveModel(SymptomRetrieveModelListener listener) {
        mListener = listener;
        mSymptomAPI = mRetrofit.create(SymptomAPI.class);
    }

    @Override
    protected void onSuccess(SymptomRetrieveEntity response) {
        mListener.onReceiveSymptomListSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        if (TextUtils.isEmpty(message)) {
            mListener.onReceiveFailed(e.getMessage());
        } else {
            mListener.onReceiveFailed(message);
        }
    }

    @Override
    public void getSymptomList(String patientId) {
        fetchData(mSymptomAPI.getSymptomList(patientId, DateUtil.getStartTimeInMillOfToday(), DateUtil.getEndTimeInMillOfToday()),true);
    }

    public interface SymptomRetrieveModelListener extends BaseModelListener{
        void onReceiveSymptomListSuccess(SymptomRetrieveEntity.RetValuesEntity valuesEntity);
    }
}
