package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorMyModel extends PatientBaseModel implements DoctorMyModelContract.Actions {

    private DoctorAPI mDoctorAPI;

    @Inject
    public DoctorMyModel(DoctorAPI doctorAPI) {
        mDoctorAPI = doctorAPI;
    }

    @Override
    public void getMyDoctors(String patientId, int page, int pageSize, final DoctorMyModelContract.Callback callback) {
        request(mDoctorAPI.getMyDoctors(patientId, page, pageSize), new Callback<DoctorMyEntity>() {
            @Override
            public void onSuccess(DoctorMyEntity response) {
                callback.onReceiveMyDoctorListSuccess(response.getRet_values());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
