package com.wonders.xlab.pci.doctor.module.patientinfo.prescription;

import com.wonders.xlab.pci.doctor.di.scope.FragmentScoped;
import com.wonders.xlab.pci.doctor.mvp.api.PrescriptionAPI;
import com.wonders.xlab.pci.doctor.mvp.presenter.PrescriptionPresenterContract;

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
    @FragmentScoped
    PrescriptionPresenterContract.ViewListener providePrescriptionViewListener() {
        return mViewListener;
    }

    @Provides
    @FragmentScoped
    PrescriptionAPI providePrescriptionAPI(Retrofit retrofit) {
        return retrofit.create(PrescriptionAPI.class);
    }


}
