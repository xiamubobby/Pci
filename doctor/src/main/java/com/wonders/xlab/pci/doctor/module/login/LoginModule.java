package com.wonders.xlab.pci.doctor.module.login;

import com.wonders.xlab.pci.doctor.di.scope.ActivityScoped;
import com.wonders.xlab.pci.doctor.mvp.api.LoginAPI;
import com.wonders.xlab.pci.doctor.mvp.presenter.LoginPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class LoginModule {
    private final LoginPresenterContract.ViewListener mViewListener;

    public LoginModule(LoginPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    LoginPresenterContract.ViewListener provideLoginViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    LoginAPI provideLoginAPI(Retrofit retrofit) {
        return retrofit.create(LoginAPI.class);
    }
}
