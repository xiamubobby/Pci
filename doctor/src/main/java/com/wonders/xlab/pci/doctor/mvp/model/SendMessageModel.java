package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.SendMessageAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.SendMessageEntity;
import com.wonders.xlab.pci.doctor.mvp.entity.request.SendMessageBody;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ISendMessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 16/3/2.
 */
public class SendMessageModel extends DoctorBaseModel<SendMessageEntity> {
    private ISendMessageModel mISendMessageModel;
    private SendMessageAPI mSendMessageAPI;

    public SendMessageModel(ISendMessageModel iSendMessageModel) {
        mISendMessageModel = iSendMessageModel;
        mSendMessageAPI = mRetrofit.create(SendMessageAPI.class);
    }

    public void sendMessage(String message,String doctorTel) {
        SendMessageBody body = new SendMessageBody();

        SendMessageBody.MsgEntity msgEntity = new SendMessageBody.MsgEntity();
        msgEntity.setType("txt");
        msgEntity.setMsg(message);

        List<String> targets = new ArrayList<>();
        targets.add("166710012339552740");

        body.setMsg(msgEntity);
        body.setFrom(doctorTel);
        body.setTarget_type("chatgroups");
        body.setTarget(targets);

        fetchData(mSendMessageAPI.sendMessage(body), false);
    }

    @Override
    protected void onSuccess(SendMessageEntity response) {
        if (null != mISendMessageModel) {
            mISendMessageModel.onSendMessageSuccess();
        } else {
            mISendMessageModel.onReceiveFailed(response.getMessage());
        }
    }

    @Override
    protected void onFailed(Throwable e) {
        mISendMessageModel.onReceiveFailed("发送失败，请重试！");
    }
}
