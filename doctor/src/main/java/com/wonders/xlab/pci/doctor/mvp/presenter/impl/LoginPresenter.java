package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;
import com.wonders.xlab.pci.doctor.mvp.model.LoginModel;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.MD5Util;

/**
 * Created by hua on 16/2/25.
 */
public class LoginPresenter extends BasePresenter implements LoginModel.LoginModelListener {
    private LoginPresenterListener mILoginPresenter;
    private LoginModel mLoginModel;

    public LoginPresenter(LoginPresenterListener iLoginPresenter) {
        mILoginPresenter = iLoginPresenter;
        mLoginModel = new LoginModel(this);
        addModel(mLoginModel);
    }

    public void login(String tel, String password) {
        mLoginModel.login(tel, new MD5Util().encrypt(password).toLowerCase());
    }

    @Override
    public void loginSuccess(LoginEntity entity) {
        mILoginPresenter.loginSuccess(entity.getRet_values().getId(), entity.getRet_values().getTel(), entity.getRet_values().getAvatarUrl(),entity.getRet_values().getName());
    }

    @Override
    public void onReceiveFailed(String message) {
        mILoginPresenter.showError(message);
    }

    public interface LoginPresenterListener extends BasePresenterListener {
        void loginSuccess(String userId, String tel, String avatarUrl,String userName);
    }
}
