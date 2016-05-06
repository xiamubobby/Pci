package com.wonders.xlab.patient.module.service;

import com.wonders.xlab.patient.di.scope.ActivityScoped;
import com.wonders.xlab.patient.mvp.api.ServiceAPI;
import com.wonders.xlab.patient.mvp.model.ServiceModel;
import com.wonders.xlab.patient.mvp.presenter.ServicePresenter;
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
    ServiceAPI provideServiceAPI(Retrofit retrofit) {
        return retrofit.create(ServiceAPI.class);
    }

}
