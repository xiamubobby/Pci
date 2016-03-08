package com.wonders.xlab.patient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.main.MainActivity;
import com.wonders.xlab.patient.ott.ForceExitOtto;
import com.wonders.xlab.patient.realm.ChatRealmEntity;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import im.hua.utils.NotifyUtil;
import io.realm.Realm;
import io.realm.RealmResults;

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

        String name = message.getStringAttribute("name", "");
        String portrait = message.getStringAttribute("portrait", Constant.DEFAULT_PORTRAIT);
        String title = message.getStringAttribute("title", "");
        String content = message.getStringAttribute("content", "");
        int type = message.getIntAttribute("type", -1);//-1:默认处理，即通过环信后台发送 0:提醒 1:内容 2：强制退出(由于用户锁定账户等等原因的安全考虑)

        Realm realm = Realm.getInstance(context);

        long recordTime;

        /**
         * -1:默认处理，即通过环信后台发送
         * 0:提醒
         * 1:内容
         * 2：强制退出(由于用户锁定账户等等原因的安全考虑)
         */
        if (type == 0) {
            /**
             * 提醒类型的数据，先删除以前的提醒数据，然后在插入新接收到的提醒数据，因为提醒数据有且只会显示一条
             */

            recordTime = Long.parseLong(message.getStringAttribute("recordTime", String.valueOf(Calendar.getInstance().getTimeInMillis())));

            realm.beginTransaction();

            /**
             * 清除所有提醒数据
             */
            RealmResults<ChatRealmEntity> realmResults = realm.where(ChatRealmEntity.class).equalTo("isMessage", false).findAll();
            if (realmResults != null) {
                realmResults.clear();
            }

            /**
             * 保存当前提醒
             */
            ChatRealmEntity cacheEntity = realm.createObject(ChatRealmEntity.class);
            cacheEntity.setTitle(title);
            cacheEntity.setUpdateTime(recordTime);
            cacheEntity.setName(name);
            cacheEntity.setPortrait(portrait);
            cacheEntity.setMessage(false);
            cacheEntity.setUserId(AIManager.getInstance(context).getUserId());

            realm.commitTransaction();

            if (!AIManager.getInstance(context).isHomeShowing()) {
                new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), cacheEntity.getTitle(), MainActivity.class, R.mipmap.ic_launcher, true);
            }
            OttoManager.post(cacheEntity);
        } else if (type == 1) {
            recordTime = Long.parseLong(message.getStringAttribute("recordTime", String.valueOf(Calendar.getInstance().getTimeInMillis())));

            /**
             * 保存当前提醒
             */
            realm.beginTransaction();

            ChatRealmEntity cacheEntity = realm.createObject(ChatRealmEntity.class);
            cacheEntity.setTitle(title);
            cacheEntity.setUpdateTime(recordTime);
            cacheEntity.setName(name);
            cacheEntity.setPortrait(portrait);
            cacheEntity.setMessage(true);
            cacheEntity.setContent(content);
            cacheEntity.setUserId(AIManager.getInstance(context).getUserId());

            realm.commitTransaction();

            if (!AIManager.getInstance(context).isHomeShowing()) {
                new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), title, MainActivity.class, R.mipmap.ic_launcher, true);
            }
        } else if (type == 2) {
            OttoManager.post(new ForceExitOtto());
        } else if (type == -1) {
            try {
                /**
                 * 保存当前提醒
                 */
                realm.beginTransaction();
                /**
                 * 清除所有提醒数据
                 */
                RealmResults<ChatRealmEntity> realmResults = realm.where(ChatRealmEntity.class).equalTo("isMessage", false).findAll();
                if (realmResults != null) {
                    realmResults.clear();
                }

                ChatRealmEntity cacheEntity = realm.createObject(ChatRealmEntity.class);
                cacheEntity.setName("");
                cacheEntity.setPortrait(Constant.DEFAULT_PORTRAIT);
                cacheEntity.setUpdateTime(Calendar.getInstance().getTimeInMillis());
                cacheEntity.setTitle(new String(((TextMessageBody) message.getBody()).getMessage().getBytes(), "UTF-8"));
                cacheEntity.setMessage(false);
                cacheEntity.setUserId(AIManager.getInstance(context).getUserId());

                realm.commitTransaction();

                if (!AIManager.getInstance(context).isHomeShowing()) {
                    new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), title, MainActivity.class, R.mipmap.ic_launcher, true);
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }

    }
}
