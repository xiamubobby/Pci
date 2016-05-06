package com.wonders.xlab.patient.module.medicineremind.edit;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.MedicineRemindEditPresenterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/5/6.
 */
@Module
public class MedicineRemindEditModule {
    private MedicineRemindEditPresenterContract.ViewListener mViewListener;


    public MedicineRemindEditModule(MedicineRemindEditPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    MedicineRemindEditPresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }
}
