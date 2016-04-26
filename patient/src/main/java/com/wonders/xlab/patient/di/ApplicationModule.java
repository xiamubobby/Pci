package com.wonders.xlab.patient.di;

import android.app.Application;
import android.content.res.Resources;

import com.wonders.xlab.patient.application.XApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/4/25.
 */
@Module
public class ApplicationModule {
    private XApplication mApplication;

    public ApplicationModule(XApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    protected Resources provideApplicationContext() {
        return mApplication.getResources();
    }
}
