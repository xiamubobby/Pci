package com.wonders.xlab.pci.doctor.application;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.wonders.xlab.pci.doctor.di.ApplicationComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends Application {
    public static Realm realm;
    private static ApplicationComponent component;
    private boolean mHasShowed = false;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        // Do it on main process
        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        component = ApplicationComponent.Initializer.init(this);

        // The realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public boolean showSplash() {
        return !mHasShowed;
    }

    public void setHasShowed(boolean hasShowed) {
        mHasShowed = hasShowed;
    }
}