package com.wonders.xlab.patient.module.service;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.ServiceAPIN;
import com.wonders.xlab.patient.mvp.presenter.ServicePresenterContract;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by natsuki on 16/5/6.
 */
@Module
public class ServiceModule {
    private ServicePresenterContract.ViewListener servicePresenter;
    public ServiceModule(ServicePresenterContract.ViewListener listener) {
        servicePresenter = listener;
    }

    @Provides
    @ActivityScoped
    ServicePresenterContract.ViewListener provideServiceViewListener() {
        return servicePresenter;
    }

    @Provides
    @ActivityScoped
    ServiceAPIN provideServiceAPI(Retrofit retrofit) {
        return retrofit.create(ServiceAPIN.class);
    }

}
