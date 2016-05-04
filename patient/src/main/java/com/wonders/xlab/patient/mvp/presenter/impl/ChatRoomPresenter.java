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
import java.util.Map;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePagePresenter implements IChatRoomPresenter, ChatRoomRecordsModel.ChatRoomModelListener, SendMessageModel.SendMessageModelListener {
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
    public void getChatList(String imGroupId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mChatRoomPresenterListener.hideLoading();
            return;
        }
        mChatRoomRecordsModel.getChatRecords(imGroupId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void sendMessage(Map<String,Object> ext, long time) {
        mSendMessageModel.sendMessage(ext, time);
    }

    @Override
    public void onReceiveChatRecordSuccess(ChatRoomEntity.RetValuesEntity valuesEntity) {
        mChatRoomPresenterListener.hideLoading();

        updatePageInfo(valuesEntity.getNumber(), valuesEntity.isFirst(), valuesEntity.isLast());

        List<ChatRoomEntity.RetValuesEntity.ContentEntity> entityList = valuesEntity.getContent();

        List<ChatRoomBean> chatRoomBeanList = new ArrayList<>();

        for (ChatRoomEntity.RetValuesEntity.ContentEntity contentEntity : entityList) {
            ChatRoomBean bean;
            if (TYPE_USER.equals(contentEntity.getType()) && mPatientId.equals(String.valueOf(contentEntity.getFromWho()))) {
                //current login doctor's message
                bean = new MeChatRoomBean();
            } else {
                bean = new OthersChatRoomBean();
            }
            bean.messageId.set(contentEntity.getId());
            bean.userId.set(mPatientId);
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
            mChatRoomPresenterListener.appendChatMessageList(chatRoomBeanList);
        } else {
            mChatRoomPresenterListener.showChatMessageList(chatRoomBeanList);
        }
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mChatRoomPresenterListener.hideLoading();
        mChatRoomPresenterListener.showNetworkError(message);
    }

    @Override
    public void onSendMessageSuccess(long time) {
        mChatRoomPresenterListener.sendMessageSuccess(time);
    }

    public interface ChatRoomPresenterListener extends BasePresenterListener {
        void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void sendMessageSuccess(long time);

        void showReachTheLastPageNotice(String message);
    }
}
