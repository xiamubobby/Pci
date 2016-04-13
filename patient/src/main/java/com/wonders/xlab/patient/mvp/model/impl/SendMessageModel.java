package com.wonders.xlab.patient.mvp.model.impl;


import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ChatRoomAPI;
import com.wonders.xlab.patient.mvp.entity.SendMessageEntity;
import com.wonders.xlab.patient.mvp.entity.request.SendMessageBody;
import com.wonders.xlab.patient.mvp.model.ISendMessageModel;

import java.util.ArrayList;
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
     * @param time
     */
    @Override
    public void sendMessage(Map<String, Object> ext, long time) {
        SendMessageBody body = new SendMessageBody();

        List<String> targets = new ArrayList<>();
        targets.add(String.valueOf(ext.get("imGroupId")));

        body.setMsg(String.valueOf(ext.get("txtContent")));
        body.setFrom(String.valueOf(ext.get("patientTel")));
        body.setTargetType("chatgroups");
        body.setTargets(targets);
        body.setExt(ext);

        fetchData(mSendMessageAPI.sendMessage(body,time), false);
    }

    @Override
    protected void onSuccess(SendMessageEntity response) {
        if (null != mISendMessageModel) {
            mISendMessageModel.onSendMessageSuccess(response.getRet_values());
        } else {
            mISendMessageModel.onReceiveFailed(-1, response.getMessage());
        }
    }

    @Override
    protected void onFailed(int code, String message) {
        mISendMessageModel.onReceiveFailed(code, "发送失败，请重试！");
    }

    public interface SendMessageModelListener extends BaseModelListener {
        void onSendMessageSuccess(long time);
    }
}
