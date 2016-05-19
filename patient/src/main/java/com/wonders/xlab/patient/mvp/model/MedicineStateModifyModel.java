package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;

/**
 * Created by hua on 16/5/9.
 */
public class MedicineStateModifyModel extends PatientBaseModel implements MedicineStateModifyModelContract.Actions {
    private MedicineRemindAPI mAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public MedicineStateModifyModel(MedicineRemindAPI api) {
        mAPI = api;
    }

    @Override
    public void changeRemindState(String remindersRecordId, boolean manualCloseReminder, final MedicineStateModifyModelContract.Callback callback) {
        request(mAPI.changeRemindState(remindersRecordId, manualCloseReminder), new Callback<EmptyValueEntity>() {
            @Override
            public void onSuccess(EmptyValueEntity response) {
                callback.onChangeRemindStateSuccess(response.getMessage());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
