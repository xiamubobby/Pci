package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.AuthAPI;
import com.wonders.xlab.patient.mvp.entity.RegisterEntity;

import javax.inject.Inject;

import im.hua.utils.MD5Util;

/**
 * Created by hua on 16/5/5.
 */
public class RegisterModel extends PatientBaseModel<RegisterEntity> implements RegisterModelContract.Actions {

    private AuthAPI mAuthAPI;

    @Inject
    public RegisterModel(AuthAPI authAPI) {
        mAuthAPI = authAPI;
    }

    @Override
    public void register(String tel, String password, String capture, final RegisterModelContract.Callback callback) {
        request(mAuthAPI.register(tel, new MD5Util().encrypt(password), capture), new Callback<RegisterEntity>() {
            @Override
            public void onSuccess(RegisterEntity response) {
                callback.onRegisterSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
