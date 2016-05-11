package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicalRecordAPI;
import com.wonders.xlab.patient.mvp.entity.MedicalRecordsEntity;

import javax.inject.Inject;

/**
 * Created by jimmy on 16/5/11.
 */
public class MedicalRecordsModel extends PatientBaseModel<MedicalRecordsEntity> implements MedicalRecordsModelContract.Actions {

    private MedicalRecordAPI medicalRecordAPI;

    @Inject
    public MedicalRecordsModel(MedicalRecordAPI medicalRecordAPI) {
        this.medicalRecordAPI = medicalRecordAPI;
    }

    @Override
    public void getMedicalRecordsList(String patientId, int page, int size, final MedicalRecordsModelContract.Callback callback) {
        request(medicalRecordAPI.getMedicalRecordsList(patientId, page, size), new Callback<MedicalRecordsEntity>() {
            @Override
            public void onSuccess(MedicalRecordsEntity response) {
                callback.onReceiveMedicalRecordsSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
