package com.wonders.xlab.pci.doctor.mvp.presenter;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.ChatRoomModel;
import com.wonders.xlab.pci.doctor.mvp.model.SendMessageModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ChatRoomModelListener;
import com.wonders.xlab.pci.doctor.mvp.model.impl.SendMessageModelListener;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.ChatRoomPresenterListener;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePresenter implements ChatRoomModelListener, SendMessageModelListener {

    private String mDoctorId = "";
    private final String TYPE_USER = "User";
    private final String TYPE_DOCTOR = "Doctor";

    private ChatRoomModel mChatRoomModel;
    private SendMessageModel mSendMessageModel;

    private ChatRoomPresenterListener mIChatRoomPresenter;

    public ChatRoomPresenter(ChatRoomPresenterListener presenter, @NonNull String doctorId) {
        mDoctorId = doctorId;
        mIChatRoomPresenter = presenter;
        mChatRoomModel = new ChatRoomModel(this);
        mSendMessageModel = new SendMessageModel(this);

        addModel(mSendMessageModel);
        addModel(mChatRoomModel);
    }

    public void getChatList(String groupId) {
        mChatRoomModel.getChatList(groupId);
    }

    public void sendMessage(String message, String doctorTel, String groupId, long time) {
        mSendMessageModel.sendMessage(message, doctorTel, groupId, time);
    }

    @Override
    public void onReceiveChatRoomHistorySuccess(ChatRoomEntity chatRoomEntity, boolean shouldAppend) {
        mIChatRoomPresenter.hideLoading();

        List<ChatRoomBean> chatRoomBeanList = new ArrayList<>();

        ChatRoomEntity.RetValuesEntity ret_values = chatRoomEntity.getRet_values();

        for (int i = 0; i < ret_values.getContent().size(); i++) {
            ChatRoomEntity.RetValuesEntity.ContentEntity contentEntity = ret_values.getContent().get(i);

            if (TYPE_DOCTOR.equals(contentEntity.getType()) && mDoctorId.equals(String.valueOf(contentEntity.getFromWho()))) {
                //current login doctor's message
                MeChatRoomBean bean = new MeChatRoomBean();
                bean.portraitUrl.set(contentEntity.getAvatarUrl());
                bean.recordTime.set(DateUtil.format(contentEntity.getSendTime(), "yyy-MM-dd HH:mm"));
                bean.text.set(contentEntity.getContent());
                bean.isSending.set(false);

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

        if (shouldAppend) {
            mIChatRoomPresenter.appendChatMessageList(chatRoomBeanList);
        } else {
            mIChatRoomPresenter.showChatMessageList(chatRoomBeanList);
        }

    }

    @Override
    public void onReceiveFailed(String message) {
        mIChatRoomPresenter.hideLoading();
        mIChatRoomPresenter.showError(message);
    }

    @Override
    public void onSendMessageSuccess(long time) {
        mIChatRoomPresenter.sendMessageSuccess(time);
    }

    @Override
    public void silenceRequest() {
        mIChatRoomPresenter.hideLoading();
    }
}
