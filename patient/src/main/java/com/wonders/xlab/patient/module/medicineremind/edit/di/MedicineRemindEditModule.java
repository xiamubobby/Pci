package com.wonders.xlab.patient.module.medicineremind.edit.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.medicineremind.edit.MedicineRemindEditContract;
import com.wonders.xlab.patient.mvp.api.MedicineRemindAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/6.
 */
@Module
public class MedicineRemindEditModule {
    private MedicineRemindEditContract.ViewListener mViewListener;


    public MedicineRemindEditModule(MedicineRemindEditContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    MedicineRemindEditContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    MedicineRemindAPI provideMedicineRemindAPI(Retrofit retrofit) {
        return retrofit.create(MedicineRemindAPI.class);
    }
}
