package com.wonders.xlab.patient.mvp.model.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wonders.xlab.patient.mvp.api.LoginAPI;
import com.wonders.xlab.patient.mvp.model.listener.LoginModelListener;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.entity.LoginEntity;

import java.util.HashMap;

/**
 * Created by hua on 15/12/17.
 */
public class LoginModel extends PatientBaseModel<LoginEntity> {

    private LoginModelListener mLoginModelListener;
    private LoginAPI mLoginAPI;

    public LoginModel(LoginModelListener loginModelListener) {
        mLoginModelListener = loginModelListener;
        mLoginAPI = mRetrofit.create(LoginAPI.class);
    }

    public void login(String tel, String password) {
        HashMap<String, String> body = new HashMap<>();
        body.put("tel", tel);
        body.put("password", password);
        fetchData(mLoginAPI.login(body), true);
    }

    @Override
    protected void onSuccess(@NonNull LoginEntity response) {
        mLoginModelListener.loginSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e) {
        mLoginModelListener.onReceiveFailed(TextUtils.isEmpty(e.getMessage()) ? "登录失败，请重试！" : e.getMessage());
    }
}
