package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.PrescriptionAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.PrescriptionEntity;

import javax.inject.Inject;

/**
 * Created by jimmy on 16/5/5.
 */
public class PrescriptionModal extends DoctorBaseModel<PrescriptionEntity> implements PrescriptionModalContract.Actions {

    private PrescriptionAPI prescriptionAPI;

    @Inject
    public PrescriptionModal(PrescriptionAPI prescriptionAPI) {
        this.prescriptionAPI = prescriptionAPI;
    }


    @Override
    public void getPrescriptionList(String patient, int pageIndex, final PrescriptionModalContract.Callback callback) {
        request(prescriptionAPI.getPrescriptionList(patient, pageIndex), new Callback<PrescriptionEntity>(){
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
