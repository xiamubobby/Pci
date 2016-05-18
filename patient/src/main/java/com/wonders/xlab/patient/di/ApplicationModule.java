package com.wonders.xlab.patient.di;

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
    XApplication provideApplication() {
        return mApplication;
    }
}
