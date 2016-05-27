package com.wonders.xlab.patient.module.me.hospital.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.me.hospital.HospitalContract;
import com.wonders.xlab.patient.mvp.api.HospitalAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/27.
 */
@Module
public class HospitalModule {
    private HospitalContract.ViewListener mViewListener;

    public HospitalModule(HospitalContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    HospitalContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    HospitalAPI provideHospitalAPI(Retrofit retrofit) {
        return retrofit.create(HospitalAPI.class);
    }
}
