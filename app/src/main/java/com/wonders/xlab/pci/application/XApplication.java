package com.wonders.xlab.pci.application;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hua on 15/12/13.
 */
public class XApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bugtags.start("b3d8dc7b153c16bb34d741d78b03e4d1", this, Bugtags.BTGInvocationEventBubble);
        LeakCanary.install(this);
    }
}
