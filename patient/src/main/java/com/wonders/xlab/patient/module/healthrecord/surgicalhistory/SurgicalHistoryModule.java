package com.wonders.xlab.patient.module.healthrecord.surgicalhistory;


import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.PatientRecordAPI;
import com.wonders.xlab.patient.mvp.presenter.SurgicalHistoryPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class SurgicalHistoryModule {
    public SurgicalHistoryModule(SurgicalHistoryPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    private SurgicalHistoryPresenterContract.ViewListener mViewListener;

    @Provides
    @ActivityScoped
    SurgicalHistoryPresenterContract.ViewListener provideSurgicalHistoryViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    PatientRecordAPI providePatientRecordAPI(Retrofit retrofit) {
        return retrofit.create(PatientRecordAPI.class);
    }
}
