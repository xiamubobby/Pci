package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ChatRoomAPI;
import com.wonders.xlab.patient.mvp.entity.SendMessageEntity;
import com.wonders.xlab.patient.mvp.entity.request.SendMessageBody;
import com.wonders.xlab.patient.mvp.model.ISendMessageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/2.
 */
public class SendMessageModel extends PatientBaseModel<SendMessageEntity> implements ISendMessageModel {
    private SendMessageModelListener mISendMessageModel;
    private ChatRoomAPI mSendMessageAPI;

    public SendMessageModel(SendMessageModelListener iSendMessageModel) {
        mISendMessageModel = iSendMessageModel;
        mSendMessageAPI = mRetrofit.create(ChatRoomAPI.class);
    }

    /**
     * @param message
     * @param patientTel
     * @param imGroupId  环信id
     * @param groupId
     * @param groupName
     * @param time
     */
    @Override
    public void sendMessage(String message, String patientTel, String imGroupId, String groupId, String groupName, long time) {
        SendMessageBody body = new SendMessageBody();

        SendMessageBody.MsgEntity msgEntity = new SendMessageBody.MsgEntity();
        msgEntity.setType("txt");
        msgEntity.setMsg(message);

        List<String> targets = new ArrayList<>();
        targets.add(imGroupId);

        body.setMsg(msgEntity);
        body.setFrom(patientTel);
        body.setTarget_type("chatgroups");
        body.setTarget(targets);

        Map<String, Object> ext = new HashMap<>();
        ext.put("type", 3);//3:表示聊天信息
        ext.put("imGroupId", imGroupId);
        ext.put("groupId", groupId);
        ext.put("groupName", groupName);
        ext.put("txtContent", message);
        body.setExt(ext);

        fetchData(mSendMessageAPI.sendMessage(body,time), false);
    }

    @Override
    protected void onSuccess(SendMessageEntity response) {
        if (null != mISendMessageModel) {
            mISendMessageModel.onSendMessageSuccess(response.getRet_values());
        } else {
            mISendMessageModel.onReceiveFailed(response.getMessage());
        }
    }

    @Override
    protected void onFailed(Throwable e, String message) {
        mISendMessageModel.onReceiveFailed("发送失败，请重试！");
    }

    public interface SendMessageModelListener extends BaseModelListener {
        void onSendMessageSuccess(long time);
    }
}
