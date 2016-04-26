package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.data.api.DoctorAPI;
import com.wonders.xlab.patient.data.entity.DoctorAllEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorAllModel;

import javax.inject.Inject;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorAllModel extends PatientBaseModel<DoctorAllEntity> implements IDoctorAllModel {
    private DoctorAllModelListener mDoctorAllModelListener;

    private DoctorAPI mDoctorAPI;

    @Inject
    public DoctorAllModel(DoctorAllModelListener doctorAllModelListener,DoctorAPI doctorAPI) {
        mDoctorAllModelListener = doctorAllModelListener;
        mDoctorAPI = doctorAPI;
    }

    @Override
    protected void onSuccess(DoctorAllEntity response) {
        mDoctorAllModelListener.onReceiveAllDoctorListSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mDoctorAllModelListener.onReceiveFailed(code, message);
    }

    @Override
    public void getAllDoctorList(String patientId, int page, int pageSize) {
        request(mDoctorAPI.getAllDoctors(patientId, page, pageSize), true);
    }

    public interface DoctorAllModelListener extends BaseModelListener {
        void onReceiveAllDoctorListSuccess(DoctorAllEntity.RetValuesEntity valuesEntity);
    }
}
