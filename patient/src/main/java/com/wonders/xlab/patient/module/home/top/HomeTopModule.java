package com.wonders.xlab.patient.module.home.top;

import com.wonders.xlab.patient.di.scope.FragmentScoped;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/5/17.
 */
@Module
public class HomeTopModule {
    private HomeTopPresenterContract.ViewListener mViewListener;

    public HomeTopModule(HomeTopPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Provides
    @FragmentScoped
    HomeTopPresenterContract.ViewListener provideViewListener() {
        return mViewListener;
    }
}
