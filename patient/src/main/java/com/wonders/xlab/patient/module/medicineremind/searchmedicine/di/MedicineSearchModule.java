package com.wonders.xlab.patient.module.medicineremind.searchmedicine.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.MedicineSearchContract;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/6.
 */
@Module
public class MedicineSearchModule {
    private MedicineSearchContract.ViewListener mViewListener;

    public MedicineSearchModule(MedicineSearchContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    MedicineSearchContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    MedicineRemindAPI provideMedicineRemindAPI(Retrofit retrofit) {
        return retrofit.create(MedicineRemindAPI.class);
    }
}
