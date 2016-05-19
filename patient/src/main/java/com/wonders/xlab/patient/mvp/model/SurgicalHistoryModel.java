package com.wonders.xlab.patient.mvp.model;


import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.PatientRecordAPI;
import com.wonders.xlab.patient.mvp.entity.SurgicalHistoryEntity;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BaseModel;

/**
 * Created by hua on 16/5/5.
 */
public class SurgicalHistoryModel extends PatientBaseModel implements SurgicalHistoryModelContract.Actions{
    private PatientRecordAPI mAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public SurgicalHistoryModel(PatientRecordAPI api) {
        mAPI = api;
    }

    @Override
    public void getSurgicalHistory(String patientId, int pageIndex, final SurgicalHistoryModelContract.Callback callback) {
        request(mAPI.getSurgicalHistory(patientId, pageIndex), new BaseModel.Callback<SurgicalHistoryEntity>() {
            @Override
            public void onSuccess(SurgicalHistoryEntity response) {
                callback.onReceiveSurgicalHistorySuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
