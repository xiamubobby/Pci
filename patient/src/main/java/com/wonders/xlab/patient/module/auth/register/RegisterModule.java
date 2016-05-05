package com.wonders.xlab.patient.module.auth.register;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.AuthAPI;
import com.wonders.xlab.patient.mvp.presenter.RegisterPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class RegisterModule {
    private RegisterPresenterContract.ViewListener mViewListener;

    public RegisterModule(RegisterPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    RegisterPresenterContract.ViewListener provideRegisterPresenterListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    AuthAPI provideAuthAPI(Retrofit retrofit) {
        return retrofit.create(AuthAPI.class);
    }
}
