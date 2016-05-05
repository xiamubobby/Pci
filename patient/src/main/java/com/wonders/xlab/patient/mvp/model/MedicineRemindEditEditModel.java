package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicineRemindEditAPI;
import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import java.util.Map;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.BaseEntity;

/**
 * Created by WZH on 16/5/5.
 */
public class MedicineRemindEditEditModel extends PatientBaseModel<BaseEntity> implements MedicineRemindEditEditModelContract.Actions {

    private MedicineRemindEditAPI mMedicineRemindEditAPI;
    @Inject
    public MedicineRemindEditEditModel(MedicineRemindEditAPI medicineRemindEditAPI) {
        mMedicineRemindEditAPI = medicineRemindEditAPI;
    }


    @Override
    public void edit(Map<String, Object> ext, final MedicineRemindEditEditModelContract.Callback callback) {
        MedicineRemindEditBody body = new MedicineRemindEditBody();
        request(mMedicineRemindEditAPI.editMedicineRemind(body), new Callback<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity response) {
                callback.editSuccess();
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
