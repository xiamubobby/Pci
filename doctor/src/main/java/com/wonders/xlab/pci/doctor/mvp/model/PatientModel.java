package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.PatientAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/19.
 */
public class PatientModel extends DoctorBaseModel {

    private IPatientModel mIPatientPresenter;
    private PatientAPI mPatientAPI;

    public PatientModel(IPatientModel presenter) {
        mIPatientPresenter = presenter;
        mPatientAPI = mRetrofit.create(PatientAPI.class);
    }

    public void getPatientList() {
        fetchData(mPatientAPI.getPatientList(), new ResponseListener() {
            @Override
            public void onSuccess(BaseEntity response) {
                mIPatientPresenter.onReceivePatientSuccess((PatientEntity) response);
            }

            @Override
            public void onFailed(Throwable e) {

            }
        });
    }
}
