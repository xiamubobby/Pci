package com.wonders.xlab.patient.module.service.detail;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.ServiceAPI;
import com.wonders.xlab.patient.mvp.presenter.ServiceDetailPresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by WZH on 16/5/9.
 */
@Module
public class ServiceDetailModule {

    private ServiceDetailPresenterContract.ViewListener mServiceDetailPresenterListener;

    public ServiceDetailModule(ServiceDetailPresenterContract.ViewListener serviceDetailPresenterListener) {
        mServiceDetailPresenterListener = serviceDetailPresenterListener;
    }

    @Provides
    @ActivityScoped
    ServiceDetailPresenterContract.ViewListener provideServiceDetailPresenterListener() {
        return mServiceDetailPresenterListener;
    }

    @Provides
    @ActivityScoped
    ServiceAPI provideServiceAPI(Retrofit retrofit) {
        return retrofit.create(ServiceAPI.class);
    }
}
