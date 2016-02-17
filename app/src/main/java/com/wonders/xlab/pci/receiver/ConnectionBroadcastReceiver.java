package com.wonders.xlab.pci.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.module.otto.ConnectStateBus;

public class ConnectionBroadcastReceiver extends BroadcastReceiver {
    public ConnectionBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        NetworkInfo info = extras.getParcelable("networkInfo");
        if (info != null) {
            NetworkInfo.State state = info.getState();
            if (state == NetworkInfo.State.CONNECTED) {
                OttoManager.post(new ConnectStateBus(true));
            } else {
                OttoManager.post(new ConnectStateBus(false));
            }
        }
    }
}
