package com.wonders.xlab.patient.dagger.module;

import com.wonders.xlab.patient.mvp.presenter.HomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/3/11.
 */
@Module
public class HomeModule {
    @Provides
    HomePresenter provideHomePresenter() {
        return new HomePresenter();
    }
}
