package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.LoginAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ILoginModel;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/25.
 */
public class LoginModel extends DoctorBaseModel {
    private ILoginModel mILoginModel;
    private LoginAPI mLoginAPI;

    public LoginModel(ILoginModel iLoginModel) {
        mILoginModel = iLoginModel;
        mLoginAPI = mRetrofit.create(LoginAPI.class);
    }

    public void login(String tel, String password) {

        fetchData(mLoginAPI.login(tel, password), new ResponseListener() {
            @Override
            public void onSuccess(BaseEntity response) {
                mILoginModel.loginSuccess((LoginEntity) response);
            }

            @Override
            public void onFailed(Throwable e) {
                mILoginModel.onReceiveFailed(e.getMessage());
            }
        });
    }
}
