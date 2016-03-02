package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.MedicalRecordAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IMedicalRecordModel;

/**
 * Created by hua on 16/2/25.
 */
public class MedicalRecordModel extends DoctorBaseModel<MedicalRecordEntity> {
    private IMedicalRecordModel mIMedicalRecordModel;
    private MedicalRecordAPI mMedicalRecordAPI;


    public MedicalRecordModel(IMedicalRecordModel medicalRecordModel) {
        mIMedicalRecordModel = medicalRecordModel;
        mMedicalRecordAPI = mRetrofit.create(MedicalRecordAPI.class);
    }

    public void getMedicalRecordList(String userId) {
        fetchData(mMedicalRecordAPI.getMedicalRecordList(userId), true);
    }

    @Override
    protected void onSuccess(MedicalRecordEntity response) {
        mIMedicalRecordModel.onReceiveMedicalRecordSuccess(response);
    }

    @Override
    protected void onFailed(Throwable e) {
        mIMedicalRecordModel.onReceiveFailed("获取就诊记录失败，请重试！");
    }
}
