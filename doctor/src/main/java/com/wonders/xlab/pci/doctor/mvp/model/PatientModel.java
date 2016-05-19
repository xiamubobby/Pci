package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.PatientAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/2/19.
 */
public class PatientModel extends DoctorBaseModel implements PatientModelContract.Actions {

    private PatientAPI mPatientAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public PatientModel(PatientAPI patientAPI) {
        mPatientAPI = patientAPI;
    }

    public void getPatientList(String doctorId, final PatientModelContract.Callback callback) {
        request(mPatientAPI.getPatientList(doctorId), new Callback<PatientEntity>() {
            @Override
            public void onSuccess(PatientEntity response) {
                callback.onReceivePatientSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

}
