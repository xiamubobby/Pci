package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ChatRoomModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.SendMessageModel;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePresenter implements ChatRoomModel.ChatRoomModelListener, SendMessageModel.SendMessageModelListener {

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

    public void sendMessage(String message, String doctorTel, String ownerId, String groupName, String imGroupId, String patientId, String patientName, String patientTel, long time, String currentDoctorAvatarUrl,String fromWhoName) {
        mSendMessageModel.sendMessage(message, doctorTel, ownerId, groupName, imGroupId, time, patientId, patientName, patientTel, currentDoctorAvatarUrl,fromWhoName);
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
                bean.recordTime.set(DateUtil.formatShowDateTime(contentEntity.getSendTime(), "yyyy年MM月dd日 HH:mm", "MM月dd日 HH:mm", "HH:mm"));
                bean.text.set(contentEntity.getContent());
                bean.isSending.set(false);

                chatRoomBeanList.add(bean);
            } else {
                OthersChatRoomBean bean = new OthersChatRoomBean();
                bean.name.set(contentEntity.getName());
                bean.portraitUrl.set(contentEntity.getAvatarUrl());
                bean.recordTime.set(DateUtil.formatShowDateTime(contentEntity.getSendTime(), "yyyy年MM月dd日 HH:mm", "MM月dd日 HH:mm", "HH:mm"));
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
    public void onReceiveFailed(int code, String message) {
        mIChatRoomPresenter.hideLoading();
        mIChatRoomPresenter.showNetworkError(message);
    }

    @Override
    public void onSendMessageSuccess(long time) {
        mIChatRoomPresenter.sendMessageSuccess(time);
    }

    @Override
    public void noMoreData(String message) {
        mIChatRoomPresenter.hideLoading();
    }

    public interface ChatRoomPresenterListener extends BasePresenterListener {
        void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void sendMessageSuccess(long time);
    }
}
