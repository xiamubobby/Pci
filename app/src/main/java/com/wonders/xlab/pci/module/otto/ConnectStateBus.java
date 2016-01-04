package com.wonders.xlab.pci.module.otto;

/**
 * Created by hua on 15/12/25.
 */
public class ConnectStateBus {
    private boolean mIsConnected;

    public ConnectStateBus(boolean isConnected) {
        mIsConnected = isConnected;
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public void setConnected(boolean connected) {
        mIsConnected = connected;
    }
}
