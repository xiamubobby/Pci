package com.wonders.xlab.patient.module.auth.login;

import com.wonders.xlab.patient.mvp.presenter.ILoginPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/4/25.
 */
@Module
public class LoginPresenterModule {

    private LoginPresenter.LoginPresenterListener mLoginPresenterListener;

    public LoginPresenterModule(LoginPresenter.LoginPresenterListener loginPresenterListener) {
        mLoginPresenterListener = loginPresenterListener;
    }

    @Provides
    protected LoginPresenter.LoginPresenterListener provideLoginPresenterListener(){
        return mLoginPresenterListener;
    }

    @Provides
    protected ILoginPresenter provideILoginPresenter(LoginPresenter.LoginPresenterListener loginPresenterListener) {
        return new LoginPresenter(loginPresenterListener);
    }
}
