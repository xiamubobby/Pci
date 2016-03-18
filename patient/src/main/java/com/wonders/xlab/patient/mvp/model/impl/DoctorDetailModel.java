package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.entity.DoctorDetailEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorDetailModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorDetailModel extends PatientBaseModel<DoctorDetailEntity> implements IDoctorDetailModel{
    private DoctorDetailModelListener mDetailModelListener;

    public DoctorDetailModel(DoctorDetailModelListener detailModelListener) {
        mDetailModelListener = detailModelListener;
    }

    @Override
    protected void onSuccess(DoctorDetailEntity response) {
        mDetailModelListener.onReceiveDoctorDetailSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e) {
        mDetailModelListener.onReceiveFailed(e.getMessage());
    }

    @Override
    public void getDoctorDetailInfo(String doctorGroupId) {
        onSuccess(null);
    }

    public interface DoctorDetailModelListener extends BaseModelListener {
        void onReceiveDoctorDetailSuccess(DoctorDetailEntity.RetValuesEntity valuesEntity);
    }
}
