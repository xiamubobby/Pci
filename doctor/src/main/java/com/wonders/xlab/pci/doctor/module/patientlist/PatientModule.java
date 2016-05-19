package com.wonders.xlab.pci.doctor.module.patientlist;

import com.wonders.xlab.pci.doctor.di.scope.FragmentScoped;
import com.wonders.xlab.pci.doctor.mvp.api.PatientAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/19.
 */
@Module
public class PatientModule {
    private PatientPresenterContract.ViewListener mViewListener;

    public PatientModule(PatientPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @FragmentScoped
    PatientPresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @FragmentScoped
    PatientAPI providePatientAPI(Retrofit retrofit) {
        return retrofit.create(PatientAPI.class);
    }
}
