package com.wonders.xlab.patient.module.medicineremind.list;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindListEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/5/9.
 */
public class MedicineRemindListModel extends PatientBaseModel implements MedicineRemindListModelContract.Actions{
    private MedicineRemindAPI mAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public MedicineRemindListModel(MedicineRemindAPI api) {
        mAPI = api;
    }

    @Override
    public void getMedicineRemindList(String patientId, int page, int size, final MedicineRemindListModelContract.Callback callback) {
        request(mAPI.getMedicineRemindList(patientId, page, size), new Callback<MedicineRemindListEntity>() {
            @Override
            public void onSuccess(MedicineRemindListEntity response) {
                callback.onReceiveMedicineRemindListSuccess(response.getRet_values());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
