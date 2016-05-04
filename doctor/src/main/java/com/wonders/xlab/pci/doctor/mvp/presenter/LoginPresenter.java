package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.mvp.entity.LoginEntity;
import com.wonders.xlab.pci.doctor.mvp.model.LoginModel;
import com.wonders.xlab.pci.doctor.mvp.model.LoginModelContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.utils.MD5Util;

/**
 * Created by hua on 16/2/25.
 */
public class LoginPresenter extends BasePresenter implements LoginPresenterContract.Actions {
    private LoginPresenterContract.ViewListener mILoginPresenter;
    private LoginModelContract.Actions mLoginModel;

    @Inject
    public LoginPresenter(LoginPresenterContract.ViewListener iLoginPresenter,LoginModel loginModel) {
        mILoginPresenter = iLoginPresenter;
        mLoginModel = loginModel;
        addModel(mLoginModel);
    }

    @Override
    public void login(String tel, String password) {
        mLoginModel.login(tel, new MD5Util().encrypt(password).toLowerCase(),
                new LoginModelContract.Callback() {
                    @Override
                    public void loginSuccess(LoginEntity entity) {
                        AIManager.getInstance().saveDoctorInfo(entity.getRet_values().getId(), entity.getRet_values().getTel(), entity.getRet_values().getAvatarUrl(), entity.getRet_values().getName());

                        mILoginPresenter.loginSuccess("登录成功");
                    }

                    @Override
                    public void onReceiveFailed(int code, String message) {
                        mILoginPresenter.showNetworkError(message);
                    }
                });
    }

}
