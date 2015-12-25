package com.wonders.xlab.pci.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.activeandroid.query.Select;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;
import com.wonders.xlab.common.utils.NotifyUtil;
import com.wonders.xlab.pci.Constant;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.MainActivity;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.home.bean.YesterdayTaskBean;

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

        try {
            String name = message.getStringAttribute("name", "");
            String portrait = message.getStringAttribute("portrait", Constant.DEFAULT_PORTRAIT);
            String title = message.getStringAttribute("title", "");
            String content = message.getStringAttribute("content", "");
            int type = message.getIntAttribute("type", 0);//0:提醒 1:内容
            long recordTime = Long.parseLong(message.getStringAttribute("recordTime"));

            if (type == 0) {
                TodayTaskBean cache = new Select().from(TodayTaskBean.class).executeSingle();
                TodayTaskBean todayTaskBean = new TodayTaskBean();
                todayTaskBean.setTitle(title);
                todayTaskBean.setUpdateTime(recordTime);
                todayTaskBean.setName(name);
                todayTaskBean.setPortrait(portrait);
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
                    new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), todayTaskBean.getTitle(), MainActivity.class, R.mipmap.ic_launcher, true);
                }
                RxBus.getInstance().send(todayTaskBean);
            } else {
                YesterdayTaskBean yesterdayTaskBean = new YesterdayTaskBean();
                yesterdayTaskBean.setTitle(title);
                yesterdayTaskBean.setUpdateTime(recordTime);
                yesterdayTaskBean.setName(name);
                yesterdayTaskBean.setContent(content);
                yesterdayTaskBean.setPortrait(portrait);
                yesterdayTaskBean.save();
                if (!AIManager.getInstance(context).isHomeShowing()) {
                    new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), yesterdayTaskBean.getTitle(), MainActivity.class, R.mipmap.ic_launcher, true);
                }
                RxBus.getInstance().send(yesterdayTaskBean);
            }

        } catch (EaseMobException e) {
            TodayTaskBean cache = new Select().from(TodayTaskBean.class).executeSingle();
            try {
                TodayTaskBean todayTaskBean = new TodayTaskBean();
                todayTaskBean.setTitle(new String(((TextMessageBody) message.getBody()).getMessage().getBytes(), "UTF-8"));
                todayTaskBean.setUpdateTime(Calendar.getInstance().getTimeInMillis());
                todayTaskBean.setName("");
                todayTaskBean.setPortrait(Constant.DEFAULT_PORTRAIT);

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
                    new NotifyUtil().showNotification(context, Constant.NOTIFY_ID, context.getResources().getString(R.string.app_name), todayTaskBean.getTitle(), MainActivity.class, R.mipmap.ic_launcher, true);
                }
                RxBus.getInstance().send(todayTaskBean);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
    }
}
