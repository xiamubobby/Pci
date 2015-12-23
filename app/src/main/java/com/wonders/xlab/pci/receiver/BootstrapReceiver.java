package com.wonders.xlab.pci.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wonders.xlab.pci.service.XEMChatService;

public class BootstrapReceiver extends BroadcastReceiver {
    public BootstrapReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BootstrapReceiver", "starting emchat service");
        context.startService(new Intent(context, XEMChatService.class));
    }
}