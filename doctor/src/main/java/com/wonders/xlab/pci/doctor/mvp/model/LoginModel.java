package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.LoginAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ILoginModel;

/**
 * Created by hua on 16/2/25.
 */
public class LoginModel extends DoctorBaseModel<LoginEntity> {
    private ILoginModel mILoginModel;
    private LoginAPI mLoginAPI;

    public LoginModel(ILoginModel iLoginModel) {
        mILoginModel = iLoginModel;
        mLoginAPI = mRetrofit.create(LoginAPI.class);
    }

    public void login(String tel, String password) {

        fetchData(mLoginAPI.login(tel, password), true);
    }

    @Override
    protected void onSuccess(LoginEntity response) {
        mILoginModel.loginSuccess(response);
    }

    @Override
    protected void onFailed(Throwable e) {
        mILoginModel.onReceiveFailed("登录失败，请重试！");
    }
}
