package com.wonders.xlab.patient.module.healthrecord.testindicator;


import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.TestIndicatorAPI;
import com.wonders.xlab.patient.mvp.presenter.TestIndicatorPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by jimmy on 16/5/5.
 */
@Module
public class TestIndicatorModule {

    private final TestIndicatorPresenterContract.ViewListener mViewListener;

    public TestIndicatorModule(TestIndicatorPresenterContract.ViewListener mViewListener) {
        this.mViewListener = mViewListener;
    }

    @Provides
    @ActivityScoped
    TestIndicatorPresenterContract.ViewListener provideTestIndicatorViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    TestIndicatorAPI provideTestIndicatorAPI(Retrofit retrofit) {
        return retrofit.create(TestIndicatorAPI.class);
    }
}
