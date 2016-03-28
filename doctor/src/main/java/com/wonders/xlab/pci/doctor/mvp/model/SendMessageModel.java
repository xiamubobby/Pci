package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.SendMessageAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.SendMessageEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.request.SendMessageBody;
import com.wonders.xlab.pci.doctor.mvp.model.listener.SendMessageModelListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *  @param message
     * @param doctorTel
     * @param groupId  环信id
     * @param groupName
     * @param imGroupId
     * @param time
     */
    public void sendMessage(String message, String doctorTel, String groupId, String groupName, String imGroupId, long time) {
        SendMessageBody body = new SendMessageBody();

        SendMessageBody.MsgEntity msgEntity = new SendMessageBody.MsgEntity();
        msgEntity.setType("txt");
        msgEntity.setMsg(message);

        List<String> targets = new ArrayList<>();
        targets.add(groupId);

        body.setMsg(msgEntity);
        body.setFrom(doctorTel);
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
}
