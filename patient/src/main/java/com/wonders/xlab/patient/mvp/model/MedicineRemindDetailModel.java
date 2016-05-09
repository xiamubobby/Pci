package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindDetailEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/5/9.
 */
public class MedicineRemindDetailModel extends PatientBaseModel<MedicineRemindDetailEntity> implements MedicineRemindDetailModelContract.Actions{
    private MedicineRemindAPI mAPI;

    @Inject
    public MedicineRemindDetailModel(MedicineRemindAPI api) {
        mAPI = api;
    }

    @Override
    public void getRemindDetail(String remindersRecordId, final MedicineRemindDetailModelContract.Callback callback) {
        request(mAPI.getMedicineRemindDetail(remindersRecordId), new Callback<MedicineRemindDetailEntity>() {
            @Override
            public void onSuccess(MedicineRemindDetailEntity response) {
                callback.OnReceiveMedicineRemindDetailSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
