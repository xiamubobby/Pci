package com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory;

import com.wonders.xlab.pci.doctor.di.scope.FragmentScoped;
import com.wonders.xlab.pci.doctor.mvp.api.PatientRecordAPI;
import com.wonders.xlab.pci.doctor.mvp.presenter.SurgicalHistoryPresenterContract;

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
    @FragmentScoped
    SurgicalHistoryPresenterContract.ViewListener provideSurgicalHistoryViewListener() {
        return mViewListener;
    }

    @Provides
    @FragmentScoped
    PatientRecordAPI providePatientRecordAPI(Retrofit retrofit) {
        return retrofit.create(PatientRecordAPI.class);
    }
}
