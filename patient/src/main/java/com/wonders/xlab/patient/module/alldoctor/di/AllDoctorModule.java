package com.wonders.xlab.patient.module.alldoctor.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.alldoctor.AllDoctorContract;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class AllDoctorModule {
    private AllDoctorContract.ViewListener mViewListener;

    public AllDoctorModule(AllDoctorContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    AllDoctorContract.ViewListener provideAllDoctorViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    DoctorAPI provideDoctorAPI(Retrofit retrofit) {
        return retrofit.create(DoctorAPI.class);
    }
}
