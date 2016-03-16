package com.wonders.xlab.patient.module.auth.login.mvn.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wonders.xlab.patient.module.auth.login.mvn.api.LoginAPI;
import com.wonders.xlab.patient.module.auth.login.mvn.model.impl.ILoginModel;
import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.entity.LoginEntity;

import java.util.HashMap;

/**
 * Created by hua on 15/12/17.
 */
public class LoginModel extends PatientBaseModel<LoginEntity> {

    private ILoginModel mILoginModel;
    private LoginAPI mLoginAPI;

    public LoginModel(ILoginModel iLoginModel) {
        mILoginModel = iLoginModel;
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
        mILoginModel.loginSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(Throwable e) {
        mILoginModel.onReceiveFailed(TextUtils.isEmpty(e.getMessage()) ? "登录失败，请重试！" : e.getMessage());
    }
}
