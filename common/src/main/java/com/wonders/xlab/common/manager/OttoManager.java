package com.wonders.xlab.common.manager;

import com.squareup.otto.Bus;

/**
 * Created by hua on 15/10/30.
 */
public class OttoManager {
    private final static Bus bus = new MainThreadBus();

    public static void register(Object o) {
        bus.register(o);
    }

    public static void unregister(Object o) {
        bus.unregister(o);
    }

    public static void post(Object o) {
        bus.post(o);
    }
}
