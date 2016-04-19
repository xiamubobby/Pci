package com.wonders.xlab.pci.doctor.data.model.impl;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.data.api.SendMessageAPI;
import com.wonders.xlab.pci.doctor.data.entity.SendMessageEntity;
import com.wonders.xlab.pci.doctor.data.entity.request.SendMessageBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/2.
 */
public class SendMessageModel extends DoctorBaseModel<SendMessageEntity> {
    private SendMessageModelListener mISendMessageModel;
    private SendMessageAPI mSendMessageAPI;

    public SendMessageModel(SendMessageModelListener iSendMessageModel) {
        mISendMessageModel = iSendMessageModel;
        mSendMessageAPI = mRetrofit.create(SendMessageAPI.class);
    }

    /**
     * @param message
     * @param doctorTel
     * @param groupId  环信id
     * @param groupName
     * @param imGroupId
     * @param time
     * @param patientId
     * @param patientName
     * @param patientTel
     * @param fromWhoAvatarUrl
     */
    public void sendMessage(String message, String doctorTel, String groupId, String groupName, String imGroupId, long time, String patientId, String patientName, String patientTel, String fromWhoAvatarUrl,String fromWhoName) {
        SendMessageBody body = new SendMessageBody();

        List<String> targets = new ArrayList<>();
        targets.add(imGroupId);

        body.setMsg(message);
        body.setFrom(doctorTel);
        body.setTarget_type("chatgroups");
        body.setTarget(targets);

        Map<String, Object> ext = new HashMap<>();
        ext.put("type", 3);//3:表示聊天信息
        ext.put("imGroupId", imGroupId);
        ext.put("groupId", groupId);
        ext.put("groupName", groupName);
        ext.put("patientId", patientId);
        ext.put("patientName", patientName);
        ext.put("patientTel", patientTel);
        ext.put("txtContent", message);
        ext.put("fromWhoAvatarUrl", fromWhoAvatarUrl);
        ext.put("fromWhoName", fromWhoName);
        body.setExt(ext);

        request(mSendMessageAPI.sendMessage(body,time), false);
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
