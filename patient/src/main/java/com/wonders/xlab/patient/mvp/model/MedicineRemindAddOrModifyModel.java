package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindAddOrModifyModel extends PatientBaseModel<BaseEntity> implements MedicineRemindAddOrModifyModelContract.Actions {

    private MedicineRemindAPI mMedicineRemindAPI;

    @Inject
    public MedicineRemindAddOrModifyModel(MedicineRemindAPI medicineRemindAPI) {
        mMedicineRemindAPI = medicineRemindAPI;
    }


    @Override
    public void addOrModify(MedicineRemindEditBody body, final MedicineRemindAddOrModifyModelContract.Callback callback) {
        request(mMedicineRemindAPI.addOrModifyMedicineRemind(body), new Callback<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity response) {
                callback.addOrModifySuccess(response.getMessage());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
