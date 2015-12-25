package com.wonders.xlab.pci.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.service.XEMChatService;

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
