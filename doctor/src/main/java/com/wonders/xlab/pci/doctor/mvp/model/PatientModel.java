package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.PatientAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/19.
 */
public class PatientModel extends DoctorBaseModel<PatientEntity> {

    private PatientModelListener mIPatientPresenter;
    private PatientAPI mPatientAPI;

    public PatientModel(PatientModelListener presenter) {
        mIPatientPresenter = presenter;
        mPatientAPI = mRetrofit.create(PatientAPI.class);
    }

    public void getPatientList(String doctorId) {
        fetchData(mPatientAPI.getPatientList(doctorId), true);
    }

    @Override
    protected void onSuccess(PatientEntity response) {
        mIPatientPresenter.onReceivePatientSuccess(response);
    }

    @Override
    protected void onFailed(String message) {
        mIPatientPresenter.onReceiveFailed(message);
    }

    public interface PatientModelListener extends BaseModelListener {
        void onReceivePatientSuccess(PatientEntity entity);
    }
}
