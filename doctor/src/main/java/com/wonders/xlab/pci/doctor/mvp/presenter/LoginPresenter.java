package com.wonders.xlab.pci.doctor.mvp.presenter;

import android.os.Handler;

import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;
import com.wonders.xlab.pci.doctor.mvp.model.LoginModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ILoginModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.ILoginPresenter;

import im.hua.library.base.mvp.BasePresenter;

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

    public void login() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginSuccess(null);
            }
        }, 3000);
//        mLoginModel.login();
    }

    @Override
    public void loginSuccess(LoginEntity entity) {
        mILoginPresenter.loginSuccess("1", "13800138000");
    }

    @Override
    public void onReceiveFailed(String message) {
        mILoginPresenter.showError(message);
    }
}
