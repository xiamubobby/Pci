package com.wonders.xlab.patient.module.auth.login.mvn.presenter;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.auth.login.mvn.model.LoginModel;
import com.wonders.xlab.patient.module.auth.login.mvn.model.impl.ILoginModel;
import com.wonders.xlab.patient.module.auth.login.mvn.presenter.impl.ILoginPresenter;
import com.wonders.xlab.patient.mvp.entity.LoginEntity;

import im.hua.library.base.mvp.BasePresenter;
import im.hua.utils.MD5Util;


/**
 * Created by hua on 16/3/16.
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
        mLoginModel.login(tel, new MD5Util().encrypt(password));
    }

    @Override
    public void loginSuccess(LoginEntity.RetValuesEntity value) {
        mILoginPresenter.hideLoading();
        mILoginPresenter.loginSuccess(value.getId(), value.getTel(), Constant.DEFAULT_PORTRAIT, "");
    }

    @Override
    public void onReceiveFailed(String message) {
        mILoginPresenter.hideLoading();
        mILoginPresenter.showError(message);
    }
}
