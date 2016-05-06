package com.wonders.xlab.patient.module.medicineremind.searchmedicine;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.MedicineSearchPresenterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/5/6.
 */
@Module
public class MedicineSearchModule {
    private MedicineSearchPresenterContract.ViewListener mViewListener;

    public MedicineSearchModule(MedicineSearchPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    MedicineSearchPresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }
}
