package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.di.DaggerModelComponent;
import com.wonders.xlab.patient.di.ModelModule;
import com.wonders.xlab.patient.mvp.model.ILoginModel;
import com.wonders.xlab.patient.mvp.model.impl.LoginModel;
import com.wonders.xlab.patient.mvp.presenter.ILoginPresenter;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/3/16.
 */
public class LoginPresenter extends BasePresenter implements ILoginPresenter, LoginModel.LoginModelListener {

    private LoginPresenterListener mLoginPresenterListener;
    private ILoginModel mLoginModel;

    public LoginPresenter(LoginPresenterListener loginPresenterListener) {
        mLoginPresenterListener = loginPresenterListener;

        mLoginModel = DaggerModelComponent.builder()
                .modelModule(new ModelModule(this))
                .build()
                .getLoginModel();

        addModel(mLoginModel);
    }

    @Override
    public void login(String tel, String password) {
        mLoginPresenterListener.showLoading("正在登录，请稍候...");
        mLoginModel.login(tel, password);
    }

    @Override
    public void loginSuccess() {
        mLoginPresenterListener.hideLoading();
        mLoginPresenterListener.loginSuccess();
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mLoginPresenterListener.hideLoading();
        mLoginPresenterListener.showNetworkError(message);
    }

    public interface LoginPresenterListener extends BasePresenterListener {
        void loginSuccess();
    }
}
