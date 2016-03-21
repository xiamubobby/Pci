package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.patient.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.patient.mvp.model.IChatRoomRecordsModel;
import com.wonders.xlab.patient.mvp.model.ISendMessageModel;
import com.wonders.xlab.patient.mvp.model.impl.ChatRoomRecordsModel;
import com.wonders.xlab.patient.mvp.model.impl.SendMessageModel;
import com.wonders.xlab.patient.mvp.presenter.IChatRoomPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;


/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePresenter implements IChatRoomPresenter, ChatRoomRecordsModel.ChatRoomModelListener, SendMessageModel.SendMessageModelListener {
    private final String TYPE_USER = "User";
    private final String TYPE_DOCTOR = "Doctor";

    private String mPatientId;

    private IChatRoomRecordsModel mChatRoomRecordsModel;
    private ISendMessageModel mSendMessageModel;

    private ChatRoomPresenterListener mChatRoomPresenterListener;

    public ChatRoomPresenter(ChatRoomPresenterListener presenter, String patientId) {
        this.mPatientId = patientId;
        mChatRoomPresenterListener = presenter;

        mChatRoomRecordsModel = new ChatRoomRecordsModel(this);
        mSendMessageModel = new SendMessageModel(this);
        addModel(mChatRoomRecordsModel);
        addModel(mSendMessageModel);
    }

    @Override
    public void getChatList(String groupId) {
        mChatRoomRecordsModel.getChatRecords(groupId);
    }

    @Override
    public void sendMessage(String message, String patientTel, String groupId, long time) {
        mSendMessageModel.sendMessage(message, patientTel, groupId, time);
    }

    @Override
    public void onReceiveChatRecordSuccess(ChatRoomEntity.RetValuesEntity valuesEntity) {
        mChatRoomPresenterListener.hideLoading();
        List<ChatRoomEntity.RetValuesEntity.ContentEntity> entityList = valuesEntity.getContent();

        List<ChatRoomBean> chatRoomBeanList = new ArrayList<>();

        for (ChatRoomEntity.RetValuesEntity.ContentEntity contentEntity : entityList) {
            if (TYPE_USER.equals(contentEntity.getType()) && mPatientId.equals(String.valueOf(contentEntity.getFromWho()))) {
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
        mChatRoomPresenterListener.showChatMessageList(chatRoomBeanList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mChatRoomPresenterListener.hideLoading();
        mChatRoomPresenterListener.showError(message);
    }

    @Override
    public void onSendMessageSuccess(long time) {
        mChatRoomPresenterListener.sendMessageSuccess(time);
    }

    public interface ChatRoomPresenterListener extends BasePresenterListener {
        void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void sendMessageSuccess(long time);
    }
}
