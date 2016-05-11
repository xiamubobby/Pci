package com.wonders.xlab.patient.module.healthrecord.medicalrecords;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.MedicalRecordAPI;
import com.wonders.xlab.patient.mvp.presenter.MedicalRecordsPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by jimmy on 16/5/11.
 */

@Module
public class MedicalRecordsModule {

    private MedicalRecordsPresenterContract.ViewListener mViewListener;

    public MedicalRecordsModule(MedicalRecordsPresenterContract.ViewListener mViewListener) {
        this.mViewListener = mViewListener;
    }

    @Provides
    @ActivityScoped
    MedicalRecordsPresenterContract.ViewListener provideHealthRecordsViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    MedicalRecordAPI provideMedicalRecordsAPI(Retrofit retrofit) {
        return retrofit.create(MedicalRecordAPI.class);
    }
}
