package com.wonders.xlab.patient.di;

import android.app.Application;
import android.content.res.Resources;

import com.wonders.xlab.patient.application.XApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hua on 16/4/25.
 * for {@link ApplicationComponent}
 */
@Module
public class ApplicationModule {
    private XApplication mApplication;

    public ApplicationModule(XApplication application) {
        mApplication = application;
    }

    @Provides
    protected Application provideApplication() {
        return mApplication;
    }

    @Provides
    protected Resources provideApplicationContext() {
        return mApplication.getResources();
    }
}
