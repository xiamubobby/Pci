package com.wonders.xlab.pci.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.activeandroid.query.Select;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.wonders.xlab.common.utils.NotifyUtil;
import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.MainActivity;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

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

        TodayTaskBean cache = new Select().from(TodayTaskBean.class).executeSingle();
        try {
            TodayTaskBean todayTaskBean = new TodayTaskBean();
            todayTaskBean.setTitle(new String(((TextMessageBody) message.getBody()).getMessage().getBytes(), "UTF-8"));
            todayTaskBean.setUpdateTime(Calendar.getInstance().getTimeInMillis());
            todayTaskBean.setName("");
            todayTaskBean.setPortrait(Constant.TEST_PORTRAIT);

            if (cache != null) {
                cache.setName(todayTaskBean.getName());
                cache.setPortrait(todayTaskBean.getPortrait());
                cache.setUpdateTime(todayTaskBean.getUpdateTime());
                cache.setTitle(todayTaskBean.getTitle());
                cache.save();
            } else {
                todayTaskBean.save();
            }
            if (!AIManager.getInstance(context).isHomeShowing()) {
                new NotifyUtil().showNotification(context,Constant.NOTIFY_ID,context.getResources().getString(R.string.app_name),todayTaskBean.getTitle(),MainActivity.class,R.mipmap.ic_launcher,true);
            }
            RxBus.getInstance().send(todayTaskBean);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
