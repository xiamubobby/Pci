package com.wonders.xlab.pci.doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.service.XEMChatService;


public class TimeClickReceiver extends BroadcastReceiver {
    public TimeClickReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AIManager.getInstance(context).hasLogin()) {
            context.startService(new Intent(context, XEMChatService.class));
        }
    }
}
