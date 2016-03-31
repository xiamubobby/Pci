package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.mvp.model.impl.LoginModel;
import com.wonders.xlab.patient.mvp.presenter.ILoginPresenter;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/3/16.
 */
public class LoginPresenter extends BasePresenter implements ILoginPresenter, LoginModel.LoginModelListener {

    private LoginPresenterListener mLoginPresenterListener;
    private LoginModel mLoginModel;

    public LoginPresenter(LoginPresenterListener loginPresenterListener) {
        mLoginPresenterListener = loginPresenterListener;

        mLoginModel = new LoginModel(this);
        addModel(mLoginModel);
    }

    @Override
    public void login(String tel, String password) {
        mLoginModel.login(tel, password);
    }

    @Override
    public void loginSuccess() {
        mLoginPresenterListener.hideLoading();
        mLoginPresenterListener.loginSuccess();
    }

    @Override
    public void onReceiveFailed(String message) {
        mLoginPresenterListener.hideLoading();
        mLoginPresenterListener.showError(message);
    }

    public interface LoginPresenterListener extends BasePresenterListener {
        void loginSuccess();
    }
}
