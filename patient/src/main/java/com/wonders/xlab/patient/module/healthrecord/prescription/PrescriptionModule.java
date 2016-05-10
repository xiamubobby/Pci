package com.wonders.xlab.patient.module.healthrecord.prescription;


import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.PrescriptionAPI;
import com.wonders.xlab.patient.mvp.presenter.PrescriptionPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by jimmy on 16/5/5.
 */
@Module
public class PrescriptionModule {

    private PrescriptionPresenterContract.ViewListener mViewListener;

    public PrescriptionModule(PrescriptionPresenterContract.ViewListener mViewListener) {
        this.mViewListener = mViewListener;
    }

    @Provides
    @ActivityScoped
    PrescriptionPresenterContract.ViewListener providePrescriptionViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    PrescriptionAPI providePrescriptionAPI(Retrofit retrofit) {
        return retrofit.create(PrescriptionAPI.class);
    }


}
