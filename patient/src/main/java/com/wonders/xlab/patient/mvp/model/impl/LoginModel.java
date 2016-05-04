package com.wonders.xlab.patient.mvp.model.impl;

import android.support.annotation.NonNull;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.mvp.api.LoginAPI;
import com.wonders.xlab.patient.mvp.entity.LoginEntity;
import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.model.ILoginModel;

import java.util.HashMap;

import javax.inject.Inject;

import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.utils.MD5Util;

/**
 * Created by hua on 15/12/17.
 */
public class LoginModel extends PatientBaseModel<LoginEntity> implements ILoginModel {

    private LoginModelListener mLoginModelListener;
    private LoginAPI mLoginAPI;

    @Inject
    public LoginModel(LoginModelListener loginModelListener,LoginAPI loginAPI) {
        mLoginModelListener = loginModelListener;
//        mLoginAPI = mRetrofit.create(LoginAPI.class);
        mLoginAPI = loginAPI;
    }

    @Override
    public void login(String tel, String password) {
        HashMap<String, String> body = new HashMap<>();
        body.put("tel", tel);
        body.put("password", new MD5Util().encrypt(password));
        request(mLoginAPI.login(body), true);
    }

    @Override
    protected void onSuccess(@NonNull LoginEntity response) {
        LoginEntity.RetValuesEntity retValues = response.getRet_values();
        if (null == retValues) {
            mLoginModelListener.onReceiveFailed(-1, "登录失败，请重试！");
            return;
        }

        /**
         * save user info
         */
        AIManager.getInstance().savePatientInfo(retValues.getId(), retValues.getTel(), retValues.getAvatarUrl(), retValues.getName());

        mLoginModelListener.loginSuccess();
    }

    @Override
    protected void onFailed(int code, String message) {
        mLoginModelListener.onReceiveFailed(code, message);
    }

    public interface LoginModelListener extends BaseModelListener {
        void loginSuccess();
    }
}
