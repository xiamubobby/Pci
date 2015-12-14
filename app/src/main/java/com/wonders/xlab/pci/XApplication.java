package com.wonders.xlab.pci;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
