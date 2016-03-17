package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorMyModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorMyModel extends PatientBaseModel<DoctorMyEntity> implements IDoctorMyModel {

    private DoctorMyModelListener mDoctorMyModelListener;
    private DoctorAPI mDoctorAPI;

    public DoctorMyModel(DoctorMyModelListener doctorMyModelListener) {
        mDoctorMyModelListener = doctorMyModelListener;
        mDoctorAPI = mRetrofit.create(DoctorAPI.class);
    }

    @Override
    protected void onSuccess(DoctorMyEntity response) {
        mDoctorMyModelListener.onReceiveMyDoctorListSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e) {
        mDoctorMyModelListener.onReceiveFailed(e.getMessage());
    }

    @Override
    public void getMyDoctors(String patientId) {
        fetchData(mDoctorAPI.getMyDoctors(patientId), true);
    }

    public interface DoctorMyModelListener extends BaseModelListener {
        void onReceiveMyDoctorListSuccess(DoctorMyEntity.RetValuesEntity valuesEntity);
    }
}
