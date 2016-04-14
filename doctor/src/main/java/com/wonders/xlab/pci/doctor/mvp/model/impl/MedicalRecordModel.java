package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.MedicalRecordAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;

import im.hua.library.base.mvp.listener.BasePageModelListener;

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
            request(mMedicalRecordAPI.getMedicalRecordList(userId, getPageIndex() + 1, getSize()), true);
        } else {
            mIMedicalRecordModel.noMoreData("");
        }
    }

    @Override
    protected void onSuccess(MedicalRecordEntity response) {
        MedicalRecordEntity.RetValuesEntity ret_values = response.getRet_values();

        if (null == ret_values) {
            mIMedicalRecordModel.onReceiveFailed(-1, "获取就诊记录失败，请重试！");
            return;
        }

        setPageIndex(ret_values.getNumber());
        setLast(ret_values.isLast());
        setFirst(ret_values.isFirst());

        mIMedicalRecordModel.onReceiveMedicalRecordSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mIMedicalRecordModel.onReceiveFailed(code, "获取就诊记录失败，请重试！");
    }

    public interface MedicalRecordModelListener extends BasePageModelListener {
        void onReceiveMedicalRecordSuccess(MedicalRecordEntity entity);
    }

}
