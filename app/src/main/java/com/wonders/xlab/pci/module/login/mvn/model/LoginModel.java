package com.wonders.xlab.pci.module.login.mvn.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wonders.xlab.pci.module.login.mvn.api.LoginAPI;
import com.wonders.xlab.pci.module.base.mvn.entity.login.LoginEntity;
import com.wonders.xlab.pci.module.login.mvn.view.LoginView;
import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;

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
        mLoginView.loginFailed(TextUtils.isEmpty(message) ? "登录失败，请重试！" : message);
    }
}
