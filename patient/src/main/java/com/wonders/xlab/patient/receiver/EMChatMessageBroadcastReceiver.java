package com.wonders.xlab.patient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.chatroom.ChatRoomActivity;
import com.wonders.xlab.patient.module.chatroom.otto.ChatRoomRecordInsertOtto;
import com.wonders.xlab.patient.module.MainActivity;
import com.wonders.xlab.patient.otto.ForceExitOtto;
import com.wonders.xlab.patient.data.realm.UnReadMessageRealm;
import com.wonders.xlab.patient.util.UnReadMessageUtil;

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
        if (!TextUtils.isEmpty(username) && username.equals(AIManager.getInstance().getPatientTel())) {
            return;
        }

        String title = message.getStringAttribute("title", "");
        int type = message.getIntAttribute("type", -1);//-1:默认处理，即通过环信后台发送 0:提醒 1:内容 2：强制退出(由于用户锁定账户等等原因的安全考虑)

        int notifyId = Constant.NOTIFY_ID_PERSISTENT;

        /**
         * -1:默认处理，即通过环信后台发送
         * 0:提醒
         * 1:内容
         * 2：强制退出(由于用户锁定账户等等原因的安全考虑)
         * 3:聊天信息
         */
        int notifyColor = 0xff30bdf2;
        if (type == 0) {
            NotifyUtil.showNotification(context, MainActivity.class, null, notifyId, context.getResources().getString(R.string.app_name), title, R.drawable.ic_notification, true, true, true, notifyColor);
        } else if (type == 1) {
            NotifyUtil.showNotification(context, MainActivity.class, null, notifyId, context.getResources().getString(R.string.app_name), title, R.drawable.ic_notification, true, true, true, notifyColor);
        } else if (type == 2) {
            OttoManager.post(new ForceExitOtto());
        } else if (3 == type) {
            String ownerId = message.getStringAttribute("ownerId", "");
            String groupName = message.getStringAttribute("groupName", "");
            String imGroupId = message.getStringAttribute("imGroupId", "");
            String txtContent = message.getStringAttribute("txtContent", "");
            String fromWhoAvatarUrl = message.getStringAttribute("fromWhoAvatarUrl", Constant.DEFAULT_PORTRAIT);
            String fromWhoName = message.getStringAttribute("fromWhoName", "");

            /**
             * 缓存未读通知数量
             */
            UnReadMessageUtil.addNewUnread(new UnReadMessageRealm(ownerId, imGroupId));

            /**
             * post event, if the current chat is showing, then append this message to chat list, or update the mydoctor page
             */
            OttoManager.post(new ChatRoomRecordInsertOtto(ownerId, groupName, imGroupId, txtContent, fromWhoAvatarUrl, fromWhoName, message.getMsgTime()));

            Bundle data = new Bundle();
            data.putString(ChatRoomActivity.EXTRA_OWNER_ID, ownerId);
            data.putString(ChatRoomActivity.EXTRA_IM_GROUP_ID, imGroupId);
            data.putString(ChatRoomActivity.EXTRA_GROUP_NAME, groupName);
            data.putBoolean(ChatRoomActivity.EXTRA_CAN_CHAT, true);

            /**
             * set the groupid as the notify id
             */
            if (TextUtils.isDigitsOnly(imGroupId)) {
                notifyId = (int) Long.parseLong(imGroupId);
            }

            NotifyUtil.showNotification(context, ChatRoomActivity.class, data, notifyId, groupName, fromWhoName + "：" + txtContent, R.drawable.ic_notification, true, true, true, notifyColor);

        } else if (type == -1) {
            try {
                title = new String(((TextMessageBody) message.getBody()).getMessage().getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            NotifyUtil.showNotification(context, ChatRoomActivity.class, null, notifyId, context.getResources().getString(R.string.app_name), title, R.drawable.ic_notification, true, true, true, notifyColor);
        }

    }
}
