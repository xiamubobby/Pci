package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorDetailModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorDetailModel extends PatientBaseModel<DoctorDetailEntity> implements IDoctorDetailModel {
    private DoctorAPI mDoctorAPI;
    private DoctorDetailModelListener mDetailModelListener;

    public DoctorDetailModel(DoctorDetailModelListener detailModelListener) {
        mDetailModelListener = detailModelListener;
        mDoctorAPI = mRetrofit.create(DoctorAPI.class);
    }

    @Override
    protected void onSuccess(DoctorDetailEntity response) {
        mDetailModelListener.onReceiveDoctorDetailSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        mDetailModelListener.onReceiveFailed(e.getMessage());
    }

    @Override
    public void getDoctorDetailInfo(String patientId,String doctorId) {
        fetchData(mDoctorAPI.getDoctorDetailInfo(patientId,doctorId), true);
    }

    public interface DoctorDetailModelListener extends BaseModelListener {
        void onReceiveDoctorDetailSuccess(DoctorDetailEntity.RetValuesEntity valuesEntity);
    }
}
