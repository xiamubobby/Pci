package com.wonders.xlab.patient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.service.XEMChatService;


public class BootstrapReceiver extends BroadcastReceiver {
    public BootstrapReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AIManager.getInstance(context).hasLogin()) {
            context.startService(new Intent(context, XEMChatService.class));
        }
    }
}