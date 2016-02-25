package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.MedicalRecordAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IMedicalRecordModel;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/25.
 */
public class MedicalRecordModel extends DoctorBaseModel {
    private IMedicalRecordModel mIMedicalRecordModel;
    private MedicalRecordAPI mMedicalRecordAPI;


    public MedicalRecordModel(IMedicalRecordModel medicalRecordModel) {
        mIMedicalRecordModel = medicalRecordModel;
        mMedicalRecordAPI = mRetrofit.create(MedicalRecordAPI.class);
    }

    public void getMedicalRecordList() {
        fetchData(mMedicalRecordAPI.getMedicalRecordList(), new ResponseListener() {
            @Override
            public void onSuccess(BaseEntity response) {
                mIMedicalRecordModel.onReceiveMedicalRecordSuccess((MedicalRecordEntity) response);
            }

            @Override
            public void onFailed(Throwable e) {
                mIMedicalRecordModel.onReceiveFailed(e.getMessage());
            }
        });
    }
}
