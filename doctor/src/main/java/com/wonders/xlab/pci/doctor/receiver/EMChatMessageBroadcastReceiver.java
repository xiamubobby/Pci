package com.wonders.xlab.pci.doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.MainActivity;
import com.wonders.xlab.pci.doctor.otto.ForceExitOtto;

import java.io.UnsupportedEncodingException;

import im.hua.utils.NotifyUtil;

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
        //忽略自己发送的消息
        if (!TextUtils.isEmpty(username) && username.equals("doctor" + AIManager.getInstance(context).getUserTel())) {
            return;
        }
        // 如果是群聊消息，获取到group id
//        if (message.getChatType() == EMMessage.ChatType.GroupChat) {
//            if (!message.getTo().equals(username)) {
//                // 消息不是发给当前会话，return
////                return;
//            }
//        }
        String name = message.getStringAttribute("name", "");
        String title = message.getStringAttribute("title", "");
        String content = message.getStringAttribute("content", "");
        int type = message.getIntAttribute("type", -1);//-1:默认处理，即通过环信后台发送 0:提醒 1:内容 2：强制退出(由于用户锁定账户等等原因的安全考虑)

        /**
         * -1:默认处理，即通过环信后台发送
         * 0:提醒
         * 1:内容
         * 2：强制退出(由于用户锁定账户等等原因的安全考虑)
         */
        if (type == 0) {

            if (!AIManager.getInstance(context).isHomeShowing()) {
                new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), title, MainActivity.class, R.drawable.ic_notification, true, true, true, 0xff30bdf2);
            }
        } else if (type == 1) {
            if (!AIManager.getInstance(context).isHomeShowing()) {
                new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), title, MainActivity.class, R.drawable.ic_notification, true, true, true, 0xff30bdf2);
            }
        } else if (type == 2) {
            OttoManager.post(new ForceExitOtto());
        } else if (type == -1) {
            try {
                title = new String(((TextMessageBody) message.getBody()).getMessage().getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (!AIManager.getInstance(context).isHomeShowing()) {
                new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), title, MainActivity.class, R.drawable.ic_notification, true, true, true, 0xff30bdf2);
            }
        }
    }
}
