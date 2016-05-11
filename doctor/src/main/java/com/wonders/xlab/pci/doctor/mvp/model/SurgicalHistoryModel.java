package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.PatientRecordAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.SurgicalHistoryEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/5/5.
 */
public class SurgicalHistoryModel extends DoctorBaseModel<SurgicalHistoryEntity> implements SurgicalHistoryModelContract.Actions{
    private PatientRecordAPI mAPI;

    @Inject
    public SurgicalHistoryModel(PatientRecordAPI api) {
        mAPI = api;
    }

    @Override
    public void getSurgicalHistoryList(String patientId, int pageIndex, final SurgicalHistoryModelContract.Callback callback) {
        request(mAPI.getSurgicalHistory(patientId, pageIndex), new Callback<SurgicalHistoryEntity>() {
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
