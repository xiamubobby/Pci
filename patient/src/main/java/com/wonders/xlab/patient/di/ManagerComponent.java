package com.wonders.xlab.patient.di;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/4.
 */
@Singleton
@Component(modules = {ManagerModule.class})
public interface ManagerComponent {

    Retrofit getRetrofit();
}
