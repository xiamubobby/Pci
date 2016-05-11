package com.wonders.xlab.patient.module.healthrecord;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.AuthAPI;
import com.wonders.xlab.patient.mvp.presenter.HealthRecordPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/11.
 */
@Module
public class HealthRecordModule {
    private HealthRecordPresenterContract.ViewListener mViewListener;

    public HealthRecordModule(HealthRecordPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    HealthRecordPresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    AuthAPI provideUserInfoAPI(Retrofit retrofit) {
        return retrofit.create(AuthAPI.class);
    }
}
