package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindEntity;

import im.hua.library.base.mvp.listener.BasePageModelListener;

/**
 * Created by hua on 16/2/25.
 */
public class MedicineRemindModel extends PatientBaseModel<MedicineRemindEntity> {
    private MedicineRemindModelListener mIMedicineRemindModel;
    private MedicineRemindAPI mMedicineRemindAPI;

    public MedicineRemindModel(MedicineRemindModelListener medicineRemindModel) {
        mIMedicineRemindModel = medicineRemindModel;
        mMedicineRemindAPI = mRetrofit.create(MedicineRemindAPI.class);
    }

    public void getMedicalRecordList(String userId, int page, int pageSize) {
        request(mMedicineRemindAPI.getMedicineRemindList(userId, page, pageSize), true);
    }

    @Override
    protected void onSuccess(MedicineRemindEntity response) {

        if (null == response) {
            mIMedicineRemindModel.onReceiveFailed(-1, "获取就诊记录失败，请重试！");
            return;
        }

        mIMedicineRemindModel.onReceiveMedicalRecordSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mIMedicineRemindModel.onReceiveFailed(code, "获取就诊记录失败，请重试！");
    }


    public interface MedicineRemindModelListener extends BasePageModelListener {
        void onReceiveMedicalRecordSuccess(MedicineRemindEntity entity);
    }
}
