package com.wonders.xlab.patient.module.auth.login;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.LoginAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/4.
 * for LoginComponent
 */
@Module
public class LoginModule {
    private LoginContract.ViewListener mLoginPresenterListener;

    public LoginModule(LoginContract.ViewListener loginPresenterListener) {
        mLoginPresenterListener = loginPresenterListener;
    }

    /**
     * @return
     * @ActivityScoped 必须与其所在的Component一致
     */
    @Provides
    @ActivityScoped
    LoginContract.ViewListener provideLoginPresenterListener() {
        return mLoginPresenterListener;
    }

    @Provides
    @ActivityScoped
    LoginAPI provideLoginAPI(Retrofit retrofit) {
        return retrofit.create(LoginAPI.class);
    }
}
