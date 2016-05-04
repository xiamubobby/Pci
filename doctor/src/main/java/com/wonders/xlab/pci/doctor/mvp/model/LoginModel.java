package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.di.scope.ActivityScoped;
import com.wonders.xlab.pci.doctor.mvp.api.LoginAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;

import javax.inject.Inject;

/**
 * Created by hua on 16/2/25.
 * Model will not use listener mode any more, instead we use callback now, this is for dagger2
 */
@ActivityScoped
public class LoginModel extends DoctorBaseModel<LoginEntity> implements LoginModelContract.Actions {
    private LoginAPI mLoginAPI;

    @Inject
    public LoginModel(LoginAPI loginAPI) {
        mLoginAPI = loginAPI;
    }

    @Override
    public void login(String tel, String password, final LoginModelContract.Callback callback) {
        request(mLoginAPI.login(tel, password), new Callback<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity response) {
                callback.loginSuccess(response);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }
}
