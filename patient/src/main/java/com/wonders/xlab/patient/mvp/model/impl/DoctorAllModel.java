package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorAllModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorAllModel extends PatientBaseModel<DoctorAllEntity> implements IDoctorAllModel {
    private DoctorAllModelListener mDoctorAllModelListener;

    private DoctorAPI mDoctorAPI;

    public DoctorAllModel(DoctorAllModelListener doctorAllModelListener) {
        mDoctorAllModelListener = doctorAllModelListener;

        mDoctorAPI = mRetrofit.create(DoctorAPI.class);
    }

    @Override
    protected void onSuccess(DoctorAllEntity response) {
        mDoctorAllModelListener.onReceiveAllDoctorListSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int retCode, String message) {
        mDoctorAllModelListener.onReceiveFailed(message);
    }

    @Override
    public void getAllDoctorList(String patientId, int page, int pageSize) {
        fetchData(mDoctorAPI.getAllDoctors(patientId, page, pageSize), true);
    }

    public interface DoctorAllModelListener extends BaseModelListener {
        void onReceiveAllDoctorListSuccess(DoctorAllEntity.RetValuesEntity valuesEntity);
    }
}
