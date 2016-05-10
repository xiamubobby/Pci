package com.wonders.xlab.patient.mvp.model;


import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.PrescriptionAPI;
import com.wonders.xlab.patient.mvp.entity.PrescriptionEntity;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BaseModel;

/**
 * Created by jimmy on 16/5/5.
 */
public class PrescriptionModal extends PatientBaseModel implements PrescriptionModalContract.Actions {

    private PrescriptionAPI prescriptionAPI;

    @Inject
    public PrescriptionModal(PrescriptionAPI prescriptionAPI) {
        this.prescriptionAPI = prescriptionAPI;
    }


    @Override
    public void getPrescriptionList(String patient, int pageIndex, final PrescriptionModalContract.Callback callback) {
        request(prescriptionAPI.getPrescriptionList(patient, pageIndex), new BaseModel.Callback<PrescriptionEntity>(){
            @Override
            public void onSuccess(PrescriptionEntity response) {
                callback.getPrescriptionListSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
