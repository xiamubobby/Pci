package com.wonders.xlab.patient.module.alldoctor;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/3/17.
 */
public class AllDoctorModel extends PatientBaseModel implements AllDoctorContract.Model {

    private DoctorAPI mDoctorAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    AllDoctorModel(DoctorAPI doctorAPI) {
        mDoctorAPI = doctorAPI;
    }

    @Override
    public void getAllDoctorList(String patientId, int page, int pageSize, final AllDoctorContract.Callback callback) {
        request(mDoctorAPI.getAllDoctors(patientId, page, pageSize), new Callback<DoctorAllEntity>() {
            @Override
            public void onSuccess(DoctorAllEntity response) {
                callback.onReceiveAllDoctorListSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
