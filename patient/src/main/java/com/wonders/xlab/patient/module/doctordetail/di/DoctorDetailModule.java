package com.wonders.xlab.patient.module.doctordetail.di;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.module.doctordetail.DoctorDetailOrGroupDetailContract;
import com.wonders.xlab.patient.mvp.api.DoctorAPI;
import com.wonders.xlab.patient.mvp.api.OrderPackageServiceAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by WZH on 16/6/22.
 */
@Module
public class DoctorDetailModule {

    private DoctorDetailOrGroupDetailContract.ViewListener mViewListener;

    public DoctorDetailModule(DoctorDetailOrGroupDetailContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @ActivityScoped
    DoctorDetailOrGroupDetailContract.ViewListener provideDoctorDetailOrGroupDetailViewListener() {
        return mViewListener;
    }

    @Provides
    @ActivityScoped
    OrderPackageServiceAPI provideOrderPackageServiceAPI(Retrofit retrofit) {
        return retrofit.create(OrderPackageServiceAPI.class);
    }

    @Provides
    @ActivityScoped
    DoctorAPI provideDoctorAPI(Retrofit retrofit) {
        return retrofit.create(DoctorAPI.class);
    }
}
