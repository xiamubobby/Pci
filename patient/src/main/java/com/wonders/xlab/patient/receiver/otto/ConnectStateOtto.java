package com.wonders.xlab.patient.receiver.otto;

/**
 * Created by hua on 16/3/8.
 */
public class ConnectStateOtto {
    private boolean mIsConnected;

    public ConnectStateOtto(boolean isConnected) {
        mIsConnected = isConnected;
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public void setConnected(boolean connected) {
        mIsConnected = connected;
    }
}
