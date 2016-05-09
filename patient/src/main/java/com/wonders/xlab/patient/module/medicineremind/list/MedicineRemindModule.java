package com.wonders.xlab.patient.module.medicineremind.list;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;
import com.wonders.xlab.patient.mvp.presenter.MedicineRemindPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

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

    @Provides
    @ActivityScoped
    MedicineRemindAPI provideMedicineRemindAPI(Retrofit retrofit) {
        return retrofit.create(MedicineRemindAPI.class);
    }
}
