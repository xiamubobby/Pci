package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.MedicalRecordAPI;
import com.wonders.xlab.pci.doctor.data.entity.MedicalRecordsEntity;
import com.wonders.xlab.pci.doctor.data.model.IMedicalRecordsModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/4.
 */
public class MedicalRecordsModel extends DoctorBaseModel<MedicalRecordsEntity> implements IMedicalRecordsModel {
    private MedicalRecordsModelListener mListener;
    private MedicalRecordAPI mAPI;

    public MedicalRecordsModel(MedicalRecordsModelListener listener) {
        mListener = listener;
        mAPI = mRetrofit.create(MedicalRecordAPI.class);
    }

    @Override
    protected void onSuccess(MedicalRecordsEntity response) {
        mListener.onReceiveMedicalRecordsSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int code, String message) {
        mListener.onReceiveFailed(code, message);
    }

    @Override
    public void getMedicalRecordsList(String patientId, int pageIndex) {
        request(mAPI.getMedicalRecordsList(patientId, pageIndex), true);
    }

    public interface MedicalRecordsModelListener extends BaseModelListener {
        void onReceiveMedicalRecordsSuccess(MedicalRecordsEntity.RetValuesEntity valuesEntity);
    }
}
