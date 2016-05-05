package com.wonders.xlab.patient.module.alldoctor;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.presenter.AllDoctorPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class AllDoctorModule {
    private AllDoctorPresenterContract.ViewListener mViewListener;

    public AllDoctorModule(AllDoctorPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    AllDoctorPresenterContract.ViewListener provideAllDoctorViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    DoctorAPI provideDoctorAPI(Retrofit retrofit) {
        return retrofit.create(DoctorAPI.class);
    }
}
