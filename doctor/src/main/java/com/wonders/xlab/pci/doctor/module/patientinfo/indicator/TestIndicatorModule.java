package com.wonders.xlab.pci.doctor.module.patientinfo.indicator;

import com.wonders.xlab.pci.doctor.di.scope.FragmentScoped;
import com.wonders.xlab.pci.doctor.mvp.api.TestIndicatorAPI;
import com.wonders.xlab.pci.doctor.mvp.presenter.TestIndicatorPresenterContract;

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
    @FragmentScoped
    TestIndicatorPresenterContract.ViewListener provideTestIndicatorViewListener() {
        return mViewListener;
    }

    @Provides
    @FragmentScoped
    TestIndicatorAPI provideTestIndicatorAPI(Retrofit retrofit) {
        return retrofit.create(TestIndicatorAPI.class);
    }
}
