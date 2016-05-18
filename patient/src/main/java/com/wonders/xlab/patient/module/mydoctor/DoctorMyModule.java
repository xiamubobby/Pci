package com.wonders.xlab.patient.module.mydoctor;

import com.wonders.xlab.patient.di.scope.FragmentScoped;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/18.
 */
@Module
public class DoctorMyModule {
    private DoctorMyPresenterContract.ViewListener mViewListener;

    public DoctorMyModule(DoctorMyPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @FragmentScoped
    DoctorMyPresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @FragmentScoped
    DoctorAPI provideDoctorAPI(Retrofit retrofit) {
        return retrofit.create(DoctorAPI.class);
    }
}
