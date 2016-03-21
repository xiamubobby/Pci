package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.PatientAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;
import com.wonders.xlab.pci.doctor.mvp.model.listener.PatientModelListener;

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
    protected void onFailed(Throwable e, String message) {
        mIPatientPresenter.onReceiveFailed("获取患者列表失败，请重试！");
    }
}
