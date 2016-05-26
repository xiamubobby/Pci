package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.base.PatientBaseModel;
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
        mDetailModelListener.onReceiveDoctorDetailSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mDetailModelListener.onReceiveFailed(code, message);
    }

    @Override
    public void getDoctorDetailInfo(String patientId,String doctorId) {
        request(mDoctorAPI.getDoctorDetailInfo(patientId,doctorId), true);
    }

    public interface DoctorDetailModelListener extends BaseModelListener {
        void onReceiveDoctorDetailSuccess(DoctorDetailEntity doctorDetailEntity);
    }
}
