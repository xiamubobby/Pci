package com.wonders.xlab.pci.doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;

public class EMChatMessageBroadcastReceiver extends BroadcastReceiver {
    public EMChatMessageBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // 注销广播
        abortBroadcast();
        // 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
        String msgId = intent.getStringExtra("msgid");
        //发送方
        String username = intent.getStringExtra("from");
        // 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
        EMMessage message = EMChatManager.getInstance().getMessage(msgId);
        if (message == null) {
            return;
        }
        // 如果是群聊消息，获取到group id
        if (message.getChatType() == EMMessage.ChatType.GroupChat) {
            if (!message.getTo().equals(username)) {
                // 消息不是发给当前会话，return
                return;
            }
        }
    }
}
