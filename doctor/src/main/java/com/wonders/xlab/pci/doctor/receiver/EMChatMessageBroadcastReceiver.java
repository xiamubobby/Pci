package com.wonders.xlab.pci.doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.chatroom.ChatRoomActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.chat.otto.ChatRoomRecordInsertOtto;
import com.wonders.xlab.pci.doctor.otto.ForceExitOtto;
import com.wonders.xlab.pci.doctor.realm.NotifiGroupInviteRealm;
import com.wonders.xlab.pci.doctor.realm.NotifiOthersRealm;
import com.wonders.xlab.pci.doctor.realm.SimpleStringRealm;
import com.wonders.xlab.pci.doctor.realm.UnReadMessageRealm;
import com.wonders.xlab.pci.doctor.util.RealmUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

import im.hua.utils.NotifyUtil;
import io.realm.RealmList;

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
        if (!TextUtils.isEmpty(username) && username.equals("doctor" + AIManager.getInstance().getDoctorTel())) {
            return;
        }

        int type = message.getIntAttribute("type", -1);//-1:默认处理，即通过环信后台发送 0:提醒 1:内容 2：强制退出(由于用户锁定账户等等原因的安全考虑)

        int notifyId = Constant.NOTIFY_ID;

        /**
         * -1:默认处理，即通过环信后台发送
         * 2：强制退出(由于用户锁定账户等等原因的安全考虑)
         * 3:聊天信息
         */
        int notifyColor = 0xff30bdf2;
        switch (type) {
            case -1:
                String title = "";
                try {
                    title = new String(((TextMessageBody) message.getBody()).getMessage().getBytes(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new NotifyUtil().showNotification(context, ChatRoomActivity.class, null, notifyId, context.getResources().getString(R.string.app_name), title, R.drawable.ic_notification, true, true, true, notifyColor);
                break;
            case 2:
                OttoManager.post(new ForceExitOtto());
                break;
            case 3:
                //聊天信息
                String groupId = message.getStringAttribute("groupId", "");
                String groupName = message.getStringAttribute("groupName", "");
                String imGroupId = message.getStringAttribute("imGroupId", "");
                String patientId = message.getStringAttribute("patientId", "");
                String patientName = message.getStringAttribute("patientName", "");
                String patientTel = message.getStringAttribute("patientTel", "");
                String txtContent = message.getStringAttribute("txtContent", "");
                String fromWhoAvatarUrl = message.getStringAttribute("fromWhoAvatarUrl", "");
                String fromWhoName = message.getStringAttribute("fromWhoName", "");

                /**
                 * 缓存未读通知数量
                 */
                RealmUtil.addNewUnread(new UnReadMessageRealm(groupId, imGroupId));
                /**
                 * post event, if the current chat is showing, then append this message to chat list, or update the mydoctor page
                 */
                OttoManager.post(new ChatRoomRecordInsertOtto(groupId, groupName, imGroupId, txtContent, fromWhoAvatarUrl, fromWhoName, message.getMsgTime()));

                Bundle data = new Bundle();
                data.putString(ChatRoomActivity.EXTRA_GROUP_ID,groupId);
                data.putString(ChatRoomActivity.EXTRA_IM_GROUP_ID,imGroupId);
                data.putString(ChatRoomActivity.EXTRA_GROUP_NAME,groupName);
                data.putString(ChatRoomActivity.EXTRA_PATIENT_ID,patientId);
                data.putString(ChatRoomActivity.EXTRA_PATIENT_NAME,patientName);
                data.putString(ChatRoomActivity.EXTRA_PATIENT_PHONE_NUMBER,patientTel);

                if (TextUtils.isDigitsOnly(groupId)) {
                    notifyId = (int) Long.parseLong(imGroupId);
                }

                new NotifyUtil().showNotification(context, ChatRoomActivity.class, data, notifyId, groupName, fromWhoName + "：" + txtContent, R.drawable.ic_notification, true, true, true, notifyColor);

                break;
            case 4:
                //邀请通知
                String gId = message.getStringAttribute("groupId", "");
                String gName = message.getStringAttribute("groupName", "");
                String gDesc = message.getStringAttribute("groupDescription", "");
                String ownerName = message.getStringAttribute("ownerName", "");
                String ownerHos = message.getStringAttribute("ownerHos", "");
                String ownerDepartment = message.getStringAttribute("ownerDepartment", "");
                String ownerJobTitle = message.getStringAttribute("ownerJobTitle", "");
                RealmList<SimpleStringRealm> avatarUrls = new RealmList<>();
                try {
                    JSONArray tmp = message.getJSONArrayAttribute("avatarUrls");
                    if (null != tmp) {
                        for (int i = 0; i < tmp.length(); i++) {
                            SimpleStringRealm stringRealm = new SimpleStringRealm();
                            stringRealm.setString(tmp.get(i).toString());
                            avatarUrls.add(stringRealm);
                        }
                    }
                } catch (EaseMobException | JSONException e) {
                    e.printStackTrace();
                }

                NotifiGroupInviteRealm realm = new NotifiGroupInviteRealm();
                realm.setGroupId(gId);
                realm.setGroupName(gName);
                realm.setGroupDesc(gDesc);
                realm.setOwnerName(ownerName);
                realm.setOwnerHospital(ownerHos);
                realm.setOwnerDepartment(ownerDepartment);
                realm.setOwnerJobTitle(ownerJobTitle);
                realm.setAvatarUrls(avatarUrls);

                RealmUtil.saveGroupInviteNotifi(realm);
                break;
            case 99:
                //其他通知，只有string
                try {
                    String txtMessage = new String(((TextMessageBody) message.getBody()).getMessage().getBytes(), "UTF-8");
                    NotifiOthersRealm othersRealm = new NotifiOthersRealm();
                    othersRealm.setMessage(txtMessage);
                    othersRealm.setReceiveTimeInMill(message.getMsgTime());
                    RealmUtil.saveOthersNotifi(othersRealm);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
