package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.PatientAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/19.
 */
public class PatientModel extends DoctorBaseModel {

    private PatientModelListener mIPatientPresenter;
    private PatientAPI mPatientAPI;

    public PatientModel(PatientModelListener presenter) {
        mIPatientPresenter = presenter;
        mPatientAPI = mRetrofit.create(PatientAPI.class);
    }

    public void getPatientList(String doctorId) {
        request(mPatientAPI.getPatientList(doctorId), new Callback<PatientEntity>() {
            @Override
            public void onSuccess(PatientEntity response) {
                mIPatientPresenter.onReceivePatientSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                mIPatientPresenter.onReceiveFailed(code, message);
            }
        });
    }

    public interface PatientModelListener extends BaseModelListener {
        void onReceivePatientSuccess(PatientEntity entity);
    }
}
