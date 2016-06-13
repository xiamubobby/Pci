package com.wonders.xlab.patient.module.medicineremind.searchmedicine;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;
import com.wonders.xlab.patient.mvp.entity.MedicineListEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/5/10.
 */
public class MedicineModel extends PatientBaseModel implements MedicineSearchContract.Model {
    private MedicineRemindAPI mAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public MedicineModel(MedicineRemindAPI api) {
        mAPI = api;
    }

    @Override
    public void search(String searchKey, final MedicineSearchContract.Callback callback) {
        request(mAPI.searchMedicineByName(searchKey), new Callback<MedicineListEntity>() {
            @Override
            public void onSuccess(MedicineListEntity response) {
                callback.onReceiveMedicinesSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

    @Override
    public void getAllMedicines(final MedicineSearchContract.Callback callback) {
        request(mAPI.getAllMedicines(), new Callback<MedicineListEntity>() {
            @Override
            public void onSuccess(MedicineListEntity response) {
                callback.onReceiveMedicinesSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
