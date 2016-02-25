package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;
import com.wonders.xlab.pci.doctor.mvp.model.LoginModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ILoginModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.ILoginPresenter;

import im.hua.library.base.mvp.BasePresenter;
import im.hua.utils.MD5Util;

/**
 * Created by hua on 16/2/25.
 */
public class LoginPresenter extends BasePresenter implements ILoginModel {
    private ILoginPresenter mILoginPresenter;
    private LoginModel mLoginModel;

    public LoginPresenter(ILoginPresenter iLoginPresenter) {
        mILoginPresenter = iLoginPresenter;
        mLoginModel = new LoginModel(this);
        addModel(mLoginModel);
    }

    public void login(String tel, String password) {
        mLoginModel.login(tel, new MD5Util().encrypt(password).toLowerCase());
    }

    @Override
    public void loginSuccess(LoginEntity entity) {
        mILoginPresenter.loginSuccess(entity.getRet_values().getId(), entity.getRet_values().getTel(), entity.getRet_values().getAvatarUrl());
    }

    @Override
    public void onReceiveFailed(String message) {
        mILoginPresenter.showError(message);
    }
}
