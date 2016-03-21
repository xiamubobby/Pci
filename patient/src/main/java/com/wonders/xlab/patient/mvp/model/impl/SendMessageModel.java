package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ChatRoomAPI;
import com.wonders.xlab.patient.mvp.entity.SendMessageEntity;
import com.wonders.xlab.patient.mvp.entity.request.SendMessageBody;
import com.wonders.xlab.patient.mvp.model.ISendMessageModel;

import java.util.ArrayList;
import java.util.List;

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
     *
     * @param message
     * @param patientTel
     * @param groupId  环信id
     * @param time
     */
    @Override
    public void sendMessage(String message, String patientTel, String groupId, long time) {
        SendMessageBody body = new SendMessageBody();

        SendMessageBody.MsgEntity msgEntity = new SendMessageBody.MsgEntity();
        msgEntity.setType("txt");
        msgEntity.setMsg(message);

        List<String> targets = new ArrayList<>();
        targets.add(groupId);

        body.setMsg(msgEntity);
        body.setFrom(patientTel);
        body.setTarget_type("chatgroups");
        body.setTarget(targets);

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
