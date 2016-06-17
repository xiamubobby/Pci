package com.wonders.xlab.patient.module.chatroom;

import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.patient.mvp.entity.ChatRoomEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;


/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePagePresenter implements ChatRoomContract.Presenter, ChatRoomContract.Callback {
    private final String TYPE_USER = "User";
    private final String TYPE_DOCTOR = "Doctor";

    @Inject
    AIManager mAIManager;
    private ChatRoomContract.ViewListener mViewListener;
    private ChatRoomContract.Model mChatRoomModel;


    @Inject
    public ChatRoomPresenter(ChatRoomContract.ViewListener viewListener, ChatRoomModel chatRoomModel) {
        mViewListener = viewListener;
        mChatRoomModel = chatRoomModel;
        addModel(mChatRoomModel);
    }

    @Override
    public void getChatList(String imGroupId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mViewListener.hideLoading();
            return;
        }
        mChatRoomModel.getChatRecords(imGroupId, getNextPageIndex(), DEFAULT_PAGE_SIZE, this);
    }

    @Override
    public void sendMessage(Map<String, Object> ext, long time) {
        mChatRoomModel.sendMessage(ext, time, this);
    }


    @Override
    public void onReceiveFailed(int code, String message) {
        mViewListener.hideLoading();
        mViewListener.showNetworkError(message);
    }

    @Override
    public void onReceiveChatRecordSuccess(ChatRoomEntity.RetValuesEntity entity) {
        mViewListener.hideLoading();

        updatePageInfo(entity.getNumber(), entity.isFirst(), entity.isLast());

        List<ChatRoomEntity.RetValuesEntity.ContentEntity> entityList = entity.getContent();

        List<ChatRoomBean> chatRoomBeanList = new ArrayList<>();

        for (ChatRoomEntity.RetValuesEntity.ContentEntity contentEntity : entityList) {
            ChatRoomBean bean;
            if (TYPE_USER.equals(contentEntity.getType()) && mAIManager.getPatientId().equals(String.valueOf(contentEntity.getFromWho()))) {
                //current login doctor's message
                bean = new MeChatRoomBean();
            } else {
                bean = new OthersChatRoomBean();
            }
            bean.messageId.set(contentEntity.getId());
            bean.userId.set(mAIManager.getPatientId());
            bean.name.set(contentEntity.getName());
            bean.portraitUrl.set(contentEntity.getAvatarUrl());
            bean.recordTimeInMill.set(contentEntity.getSendTime());
            bean.text.set(contentEntity.getContent());
            bean.isSending.set(false);
            chatRoomBeanList.add(bean);

        }
        if (chatRoomBeanList.size() <= 0) {
            return;
        }
        if (shouldAppend()) {
            mViewListener.appendChatMessageList(chatRoomBeanList);
        } else {
            mViewListener.showChatMessageList(chatRoomBeanList);
        }
    }

    @Override
    public void onSendMessageSuccess(long time) {
        mViewListener.sendMessageSuccess(time);
    }

}
