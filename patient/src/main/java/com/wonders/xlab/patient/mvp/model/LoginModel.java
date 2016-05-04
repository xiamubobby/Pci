package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.LoginAPI;
import com.wonders.xlab.patient.mvp.entity.LoginEntity;

import java.util.HashMap;

import javax.inject.Inject;

import im.hua.utils.MD5Util;

/**
 * Created by hua on 15/12/17.
 */
@ActivityScoped
public class LoginModel extends PatientBaseModel<LoginEntity> implements LoginModelContract.Actions {

    private LoginAPI mLoginAPI;

    @Inject
    public LoginModel(LoginAPI loginAPI) {
        mLoginAPI = loginAPI;
    }

    @Override
    public void login(String tel, String password, final LoginModelContract.Callback callback) {
        HashMap<String, String> body = new HashMap<>();
        body.put("tel", tel);
        body.put("password", new MD5Util().encrypt(password));
        request(mLoginAPI.login(body), new Callback<LoginEntity>() {
            @Override
            public void onSuccess(LoginEntity response) {
                LoginEntity.RetValuesEntity retValues = response.getRet_values();
                if (null == retValues) {
                    callback.onReceiveFailed(-1, "登录失败，请重试！");
                    return;
                }

                /**
                 * save user info
                 */
                AIManager.getInstance().savePatientInfo(retValues.getId(), retValues.getTel(), retValues.getAvatarUrl(), retValues.getName());

                callback.loginSuccess();
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });

    }

}
