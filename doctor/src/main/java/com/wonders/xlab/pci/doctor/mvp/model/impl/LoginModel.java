package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.LoginAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/2/25.
 */
public class LoginModel extends DoctorBaseModel<LoginEntity> {
    private LoginModelListener mILoginModel;
    private LoginAPI mLoginAPI;

    public LoginModel(LoginModelListener iLoginModel) {
        mILoginModel = iLoginModel;
        mLoginAPI = mRetrofit.create(LoginAPI.class);
    }

    public void login(String tel, String password) {

        request(mLoginAPI.login(tel, password), true);
    }

    @Override
    protected void onSuccess(LoginEntity response) {
        mILoginModel.loginSuccess(response);
    }

    @Override
    protected void onFailed(int code, String message) {
        mILoginModel.onReceiveFailed(code, message);
    }

    public interface LoginModelListener extends BaseModelListener {
        void loginSuccess(LoginEntity entity);
    }
}
