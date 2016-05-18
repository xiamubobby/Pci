package com.wonders.xlab.patient.module.home;

import com.wonders.xlab.patient.di.scope.FragmentScoped;
import com.wonders.xlab.patient.mvp.api.BannerAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by hua on 16/5/18.
 */
@Module
public class HomeModule {
    private HomePresenterContract.ViewListener mViewListener;

    public HomeModule(HomePresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @FragmentScoped
    HomePresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }

    @Provides
    @FragmentScoped
    BannerAPI provideBannerAPI(Retrofit retrofit) {
        return retrofit.create(BannerAPI.class);
    }
}
