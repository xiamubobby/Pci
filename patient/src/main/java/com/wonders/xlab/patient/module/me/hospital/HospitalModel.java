package com.wonders.xlab.patient.module.me.hospital;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.HospitalAPI;
import com.wonders.xlab.patient.mvp.entity.HospitalAllEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/5/27.
 */
public class HospitalModel extends PatientBaseModel<HospitalAllEntity> implements HospitalContract.Model{
    private HospitalAPI mAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public HospitalModel(HospitalAPI api) {
        mAPI = api;
    }

    @Override
    public void getAllHospitals(final HospitalContract.Callback callback) {
        request(mAPI.getHospitals(), new Callback<HospitalAllEntity>() {
            @Override
            public void onSuccess(HospitalAllEntity response) {
                callback.onReceiveHospitalsSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

}
