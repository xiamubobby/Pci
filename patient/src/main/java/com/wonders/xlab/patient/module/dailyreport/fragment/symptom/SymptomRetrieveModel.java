package com.wonders.xlab.patient.module.dailyreport.fragment.symptom;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.SymptomAPI;
import com.wonders.xlab.patient.mvp.entity.SymptomRetrieveEntity;
import com.wonders.xlab.patient.module.dailyreport.fragment.symptom.ISymptomRetrieveModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomRetrieveModel extends PatientBaseModel<SymptomRetrieveEntity> implements ISymptomRetrieveModel {
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
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getSymptomList(String patientId, long startTime, long endTime, int page, int size) {
        request(mSymptomAPI.getSymptomList(patientId, startTime, endTime, page, size), true);
    }

    public interface SymptomRetrieveModelListener extends BaseModelListener {
        void onReceiveSymptomListSuccess(SymptomRetrieveEntity.RetValuesEntity valuesEntity);
    }
}
