package com.wonders.xlab.patient.mvp.model;

import android.util.Log;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorListEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.BaseEntity;
import im.hua.library.base.mvp.impl.BaseModel;

/**
 * Created by xiamubobby on 16/7/6.
 */

public class DoctorListModel extends PatientBaseModel implements DoctorListModelContract.Action {

    private DoctorAPI doctorAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public DoctorListModel(DoctorAPI doctorAPI) { this.doctorAPI = doctorAPI; }

    @Override
    public void getDoctorList(String patientId, final DoctorListModelContract.Callback callback) {
        Log.d("patientId", patientId);
        request(doctorAPI.getDoctorList(patientId), new Callback<DoctorListEntity>() {

            @Override
            public void onSuccess(DoctorListEntity response) {
                DoctorListEntity en = response;
                callback.onDoctorListReceiveSucceed(response);
            }

            @Override
            public void onFailed(int code, String message) {
                Log.e(code+"", message);
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
