package com.wonders.xlab.patient.module.auth.login;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;


/**
 * Created by hua on 16/3/16.
 */
public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private LoginContract.ViewListener mLoginPresenterListener;
    private LoginContract.Model mLoginModel;

    /**
     * 注入
     * 为避免人为的手动创建对象，构造函数不设为public
     *
     * @param loginPresenterListener
     * @param loginModel
     */
    @Inject
    LoginPresenter(LoginContract.ViewListener loginPresenterListener, LoginModel loginModel) {
        mLoginPresenterListener = loginPresenterListener;

        mLoginModel = loginModel;

        addModel(mLoginModel);
    }

    @Override
    public void login(String tel, String password) {
        mLoginPresenterListener.showLoading("正在登录，请稍候...");
        mLoginModel.login(tel, password, new LoginContract.Callback() {
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

}
