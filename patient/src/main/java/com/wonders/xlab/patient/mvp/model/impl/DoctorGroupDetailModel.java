package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.entity.DoctorGroupDetailEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorGroupDetailModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public class DoctorGroupDetailModel extends PatientBaseModel<DoctorGroupDetailEntity> implements IDoctorGroupDetailModel {
    private DoctorAPI mDoctorAPI;
    private DoctorGroupDetailModelListener mDetailModelListener;

    public DoctorGroupDetailModel(DoctorGroupDetailModelListener detailModelListener) {
        mDetailModelListener = detailModelListener;
        mDoctorAPI = mRetrofit.create(DoctorAPI.class);
    }

    @Override
    protected void onSuccess(DoctorGroupDetailEntity response) {
        mDetailModelListener.onReceiveDoctorGroupDetailSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e) {
        mDetailModelListener.onReceiveFailed(e.getMessage());
    }

    @Override
    public void getDoctorGroupDetailInfo(String doctorGroupId) {
        fetchData(mDoctorAPI.getDoctorGroupDetailInfo(doctorGroupId), true);
    }

    public interface DoctorGroupDetailModelListener extends BaseModelListener {
        void onReceiveDoctorGroupDetailSuccess(DoctorGroupDetailEntity.RetValuesEntity valuesEntity);
    }
}
