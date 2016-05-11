package com.wonders.xlab.patient.di;

import android.app.Application;
import android.content.res.Resources;

import com.wonders.xlab.patient.application.XApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

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
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    Resources provideApplicationContext() {
        return mApplication.getResources();
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(mApplication).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        return Realm.getDefaultInstance();
    }
}
