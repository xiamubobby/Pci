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
public class MedicineRemindEditCreateModel extends PatientBaseModel<BaseEntity> implements MedicineRemindEditCreateModelContract.Actions {

    private MedicineRemindEditAPI mMedicineRemindEditAPI;
    @Inject
    public MedicineRemindEditCreateModel(MedicineRemindEditAPI medicineRemindEditAPI) {
        mMedicineRemindEditAPI = medicineRemindEditAPI;
    }

    @Override
    public void create(Map<String, Object> ext, final MedicineRemindEditCreateModelContract.Callback callback) {
        MedicineRemindEditBody body = new MedicineRemindEditBody();
        request(mMedicineRemindEditAPI.createMedicineRemind(AIManager.getInstance().getPatientId(), body), new Callback<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity response) {
                callback.createSuccess();
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }


}
