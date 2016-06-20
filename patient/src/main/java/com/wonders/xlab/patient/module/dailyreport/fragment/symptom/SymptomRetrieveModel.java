package com.wonders.xlab.patient.module.dailyreport.fragment.symptom;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.SymptomAPI;
import com.wonders.xlab.patient.mvp.entity.SymptomRetrieveEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/3/24.
 */
public class SymptomRetrieveModel extends PatientBaseModel implements SymptomReportContract.Model {
    private SymptomAPI mSymptomAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    SymptomRetrieveModel(SymptomAPI symptomAPI) {
        mSymptomAPI = symptomAPI;
    }


    @Override
    public void getSymptomList(String patientId, long startTime, long endTime, int page, int size, final SymptomReportContract.Callback callback) {
        request(mSymptomAPI.getSymptomList(patientId, startTime, endTime, page, size), new Callback<SymptomRetrieveEntity>() {
            @Override
            public void onSuccess(SymptomRetrieveEntity response) {
                callback.onReceiveSymptomListSuccess(response.getRet_values());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

}
