package com.wonders.xlab.pci.mvn.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wonders.xlab.pci.mvn.BaseEntity;
import com.wonders.xlab.pci.mvn.BaseModel;
import com.wonders.xlab.pci.mvn.api.LoginAPI;

/**
 * Created by hua on 15/12/13.
 */
public class LoginModel extends BaseModel<BaseEntity> {
    private LoginAPI mLoginAPI;

    public void login() {
        mLoginAPI = mRetrofit.create(LoginAPI.class);
        setObservable(mLoginAPI.login());
    }

    @Override
    protected void onSuccess(@NonNull BaseEntity response) {
        Log.d("LoginModel", response.getMessage());
    }

    @Override
    protected void onFailed(String message) {
        Log.d("LoginModel", message);
    }
}
