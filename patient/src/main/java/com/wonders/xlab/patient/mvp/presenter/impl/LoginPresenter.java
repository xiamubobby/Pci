package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.mvp.entity.LoginEntity;
import com.wonders.xlab.patient.mvp.model.impl.LoginModel;
import com.wonders.xlab.patient.mvp.model.listener.LoginModelListener;
import com.wonders.xlab.patient.mvp.presenter.ILoginPresenter;
import com.wonders.xlab.patient.mvp.presenter.listener.LoginPresenterListenerListener;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.utils.MD5Util;


/**
 * Created by hua on 16/3/16.
 */
public class LoginPresenter extends BasePresenter implements ILoginPresenter, LoginModelListener {

    private LoginPresenterListenerListener mLoginPresenterListener;
    private LoginModel mLoginModel;

    public LoginPresenter(LoginPresenterListenerListener loginPresenterListener) {
        mLoginPresenterListener = loginPresenterListener;

        mLoginModel = new LoginModel(this);
        addModel(mLoginModel);
    }

    @Override
    public void login(String tel, String password) {
        mLoginModel.login(tel, new MD5Util().encrypt(password));
    }

    @Override
    public void loginSuccess(LoginEntity.RetValuesEntity value) {
        mLoginPresenterListener.hideLoading();
        mLoginPresenterListener.loginSuccess(value.getId(), value.getTel(), Constant.DEFAULT_PORTRAIT, "");
    }

    @Override
    public void onReceiveFailed(String message) {
        mLoginPresenterListener.hideLoading();
        mLoginPresenterListener.showError(message);
    }
}
