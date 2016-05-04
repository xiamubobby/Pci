package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.model.LoginModel;
import com.wonders.xlab.patient.mvp.model.LoginModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;


/**
 * Created by hua on 16/3/16.
 */
public class LoginPresenter extends BasePresenter implements LoginPresenterContract.Actions {

    private LoginPresenterContract.ViewListener mLoginPresenterListener;
    private LoginModelContract.Actions mLoginModel;

    @Inject
    public LoginPresenter(LoginPresenterContract.ViewListener loginPresenterListener,LoginModel loginModel) {
        mLoginPresenterListener = loginPresenterListener;

        mLoginModel = loginModel;

        addModel(mLoginModel);
    }

    @Override
    public void login(String tel, String password) {
        mLoginPresenterListener.showLoading("正在登录，请稍候...");
        mLoginModel.login(tel, password, new LoginModelContract.Callback() {
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
        });
    }

    /*@Override
    public void loginSuccess() {
        mLoginPresenterListener.hideLoading();
        mLoginPresenterListener.loginSuccess();
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mLoginPresenterListener.hideLoading();
        mLoginPresenterListener.showNetworkError(message);
    }*/
}
