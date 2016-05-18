package com.wonders.xlab.patient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.service.XEMChatService;
import com.wonders.xlab.patient.util.AlarmUtil;


public class BootstrapReceiver extends BroadcastReceiver {

    public BootstrapReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AIManager.getInstance().hasLogin()) {
            AlarmUtil.newInstance().scheduleMedicineRemindAlarm(context);
            context.startService(new Intent(context, XEMChatService.class));
        }
    }
}
