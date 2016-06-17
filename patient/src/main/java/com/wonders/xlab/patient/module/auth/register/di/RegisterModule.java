package com.wonders.xlab.patient.module.auth.register.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.auth.register.RegisterContract;
import com.wonders.xlab.patient.mvp.api.AuthAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class RegisterModule {
    private RegisterContract.ViewListener mViewListener;

    public RegisterModule(RegisterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    RegisterContract.ViewListener provideRegisterPresenterListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    AuthAPI provideAuthAPI(Retrofit retrofit) {
        return retrofit.create(AuthAPI.class);
    }
}
