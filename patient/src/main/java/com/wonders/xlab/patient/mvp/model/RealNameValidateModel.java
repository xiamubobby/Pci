package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AuthAPI;
import com.wonders.xlab.patient.mvp.entity.RealNameValidateEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/5/10.
 */
public class RealNameValidateModel extends PatientBaseModel implements RealNameValidateModelContract.Actions {
    private AuthAPI mAuthAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    public RealNameValidateModel(AuthAPI authAPI) {
        mAuthAPI = authAPI;
    }

    @Override
    public void getValidateResult(String patientId, final RealNameValidateModelContract.Callback callback) {
        request(mAuthAPI.getRealNameValidateState(patientId), new Callback<RealNameValidateEntity>() {
            @Override
            public void onSuccess(RealNameValidateEntity response) {
                callback.onReceiveValidateResultSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
