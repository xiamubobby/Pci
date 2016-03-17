package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.MedicalRecordAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.MedicalRecordModelListener;

/**
 * Created by hua on 16/2/25.
 */
public class MedicalRecordModel extends DoctorBaseModel<MedicalRecordEntity> {
    private MedicalRecordModelListener mIMedicalRecordModel;
    private MedicalRecordAPI mMedicalRecordAPI;

    public MedicalRecordModel(MedicalRecordModelListener medicalRecordModel) {
        mIMedicalRecordModel = medicalRecordModel;
        mMedicalRecordAPI = mRetrofit.create(MedicalRecordAPI.class);
    }

    public void getMedicalRecordList(String userId) {
        if (!isLast()) {
            fetchData(mMedicalRecordAPI.getMedicalRecordList(userId, getPageIndex() + 1, getSize()), true);
        } else {
            mIMedicalRecordModel.silenceRequest();
        }
    }

    @Override
    protected void onSuccess(MedicalRecordEntity response) {
        MedicalRecordEntity.RetValuesEntity ret_values = response.getRet_values();

        if (null == ret_values) {
            mIMedicalRecordModel.onReceiveFailed("获取就诊记录失败，请重试！");
            return;
        }

        setPageIndex(ret_values.getNumber());
        setLast(ret_values.isLast());
        setFirst(ret_values.isFirst());

        mIMedicalRecordModel.onReceiveMedicalRecordSuccess(response);
    }

    @Override
    protected void onFailed(Throwable e) {
        mIMedicalRecordModel.onReceiveFailed("获取就诊记录失败，请重试！");
    }
}
