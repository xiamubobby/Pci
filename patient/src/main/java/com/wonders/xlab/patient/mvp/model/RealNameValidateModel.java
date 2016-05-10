package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AuthAPI;

import javax.inject.Inject;

import im.hua.library.base.mvp.entity.EmptyValueEntity;

/**
 * Created by hua on 16/5/10.
 */
public class RealNameValidateModel extends PatientBaseModel implements RealNameValidateModelContract.Actions {
    private AuthAPI mAuthAPI;

    @Inject
    public RealNameValidateModel(AuthAPI authAPI) {
        mAuthAPI = authAPI;
    }

    @Override
    public void getValidateResult(String patientId, final RealNameValidateModelContract.Callback callback) {
        request(mAuthAPI.getRealNameValidateState(patientId), new Callback<EmptyValueEntity>() {
            @Override
            public void onSuccess(EmptyValueEntity response) {
                callback.onReceiveValidateResultSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
