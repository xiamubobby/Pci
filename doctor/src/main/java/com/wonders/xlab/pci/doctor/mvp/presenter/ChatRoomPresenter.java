package com.wonders.xlab.pci.doctor.mvp.presenter;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.ChatRoomModel;
import com.wonders.xlab.pci.doctor.mvp.model.SendMessageModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IChatRoomModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ISendMessageModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IChatRoomPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePresenter implements IChatRoomModel, ISendMessageModel {

    private String mDoctorId = "";
    private final String TYPE_USER = "User";
    private final String TYPE_DOCTOR = "Doctor";

    private ChatRoomModel mChatRoomModel;
    private SendMessageModel mSendMessageModel;

    private IChatRoomPresenter mIChatRoomPresenter;

    private int page;
    private int size = 20;

    public ChatRoomPresenter(IChatRoomPresenter presenter, @NonNull String doctorId) {
        mDoctorId = doctorId;
        mIChatRoomPresenter = presenter;
        mChatRoomModel = new ChatRoomModel(this);
        mSendMessageModel = new SendMessageModel(this);

        addModel(mSendMessageModel);
        addModel(mChatRoomModel);
    }

    public void getChatList(String groupId) {
        mChatRoomModel.getChatList(groupId, page, size);
    }

    public void sendMessage(String message, String doctorTel) {
        mSendMessageModel.sendMessage(message, doctorTel);
    }

    @Override
    public void onReceiveChatRoomHistorySuccess(ChatRoomEntity chatRoomEntity) {
        List<ChatRoomBean> chatRoomBeanList = new ArrayList<>();
        if (null == chatRoomEntity.getRet_values()) {
            mIChatRoomPresenter.showError("获取数据出错，请重试！");
            return;
        }
        for (int i = 0; i < chatRoomEntity.getRet_values().getContent().size(); i++) {
            ChatRoomEntity.RetValuesEntity.ContentEntity contentEntity = chatRoomEntity.getRet_values().getContent().get(i);

            if (TYPE_DOCTOR.equals(contentEntity.getType()) && mDoctorId.equals(String.valueOf(contentEntity.getFromWho()))) {
                //current user's message
                MeChatRoomBean bean = new MeChatRoomBean();
                bean.name.set(contentEntity.getName());
                bean.portraitUrl.set(contentEntity.getAvatarUrl());
                bean.recordTime.set(DateUtil.format(contentEntity.getSendTime(), "yyy-MM-dd HH:mm"));
                bean.text.set(contentEntity.getContent());

                chatRoomBeanList.add(bean);
            } else {
                OthersChatRoomBean bean = new OthersChatRoomBean();
                bean.name.set(contentEntity.getName());
                bean.portraitUrl.set(contentEntity.getAvatarUrl());
                bean.recordTime.set(DateUtil.format(contentEntity.getSendTime(), "yyy-MM-dd HH:mm"));
                bean.text.set(contentEntity.getContent());

                chatRoomBeanList.add(bean);
            }
        }
        if (mIChatRoomPresenter != null) {
            mIChatRoomPresenter.showChatMessageList(chatRoomBeanList);
        }

    }

    @Override
    public void onReceiveFailed(String message) {
        mIChatRoomPresenter.showError(message);
    }

    @Override
    public void onSendMessageSuccess() {
        mIChatRoomPresenter.sendMessageSuccess();
    }
}
