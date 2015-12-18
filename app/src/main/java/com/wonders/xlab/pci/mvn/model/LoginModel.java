package com.wonders.xlab.pci.mvn.model;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.LoginAPI;
import com.wonders.xlab.pci.mvn.entity.LoginEntity;
import com.wonders.xlab.pci.mvn.view.LoginView;

import java.util.HashMap;

/**
 * Created by hua on 15/12/17.
 */
public class LoginModel extends BaseModel<LoginEntity> {

    private LoginView mLoginView;
    private LoginAPI mLoginAPI;

    public LoginModel(LoginView loginView) {
        mLoginView = loginView;
        mLoginAPI = mRetrofit.create(LoginAPI.class);
    }

    public void login(String tel, String password) {
        HashMap<String, String> body = new HashMap<>();
        body.put("tel", tel);
        body.put("password", password);
        setObservable(mLoginAPI.login(body));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoginView.showLoading();
    }

    @Override
    protected void onSuccess(@NonNull LoginEntity response) {
        mLoginView.hideLoading();
        if (response.getRet_values() == null) {
            mLoginView.loginFailed("登录失败，请重试！");
        } else {
            mLoginView.loginSuccess(response.getRet_values());
        }
    }

    @Override
    protected void onFailed(String message) {
        mLoginView.hideLoading();
        mLoginView.loginFailed(message);
    }
}
