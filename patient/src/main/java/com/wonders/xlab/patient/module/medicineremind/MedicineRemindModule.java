package com.wonders.xlab.patient.module.medicineremind;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.presenter.MedicineRemindPresenterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/5/5.
 */
@Module
public class MedicineRemindModule {
    private MedicineRemindPresenterContract.ViewListener mViewListener;

    public MedicineRemindModule(MedicineRemindPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    MedicineRemindPresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }

}
