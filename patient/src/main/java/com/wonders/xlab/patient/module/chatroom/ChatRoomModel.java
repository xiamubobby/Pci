package com.wonders.xlab.patient.module.chatroom;

import com.wonders.xlab.patient.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ChatRoomAPI;
import com.wonders.xlab.patient.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.patient.mvp.entity.SendMessageEntity;
import com.wonders.xlab.patient.mvp.entity.request.SendMessageBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by WZH on 16/6/16.
 */
public class ChatRoomModel extends PatientBaseModel implements ChatRoomContract.Model {

    private ChatRoomAPI mChatRoomAPI;

    @Override
    public boolean useDagger() {
        return true;
    }

    @Inject
    ChatRoomModel(ChatRoomAPI chatRoomAPI) {
        mChatRoomAPI = chatRoomAPI;
    }

    @Override
    public void getChatRecords(String imGroupId, int page, int pageSize, final ChatRoomContract.Callback callback) {
        request(mChatRoomAPI.getChatRecords(imGroupId, page, pageSize), new Callback<ChatRoomEntity>() {
            @Override
            public void onSuccess(ChatRoomEntity response) {
                callback.onReceiveChatRecordSuccess(response.getRet_values());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, message);
            }
        });
    }

    @Override
    public void sendMessage(Map<String, Object> ext, long time, final ChatRoomContract.Callback callback) {
        SendMessageBody body = new SendMessageBody();

        List<String> targets = new ArrayList<>();
        targets.add(String.valueOf(ext.get("imGroupId")));

        body.setMsg(String.valueOf(ext.get("txtContent")));
        body.setFrom(String.valueOf(ext.get("patientTel")));
        body.setTargetType("chatgroups");
        body.setTargets(targets);
        body.setExt(ext);
        request(mChatRoomAPI.sendMessage(body, time), new Callback<SendMessageEntity>() {
            @Override
            public void onSuccess(SendMessageEntity response) {
                callback.onSendMessageSuccess(response.getRet_values());
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onReceiveFailed(code, "发送失败，请重试！");
            }
        });
    }
}
