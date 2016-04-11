package com.wonders.xlab.patient.mvp.model.impl;

import com.wonders.xlab.patient.module.base.PatientBaseModel;
import com.wonders.xlab.patient.mvp.api.ChatRoomAPI;
import com.wonders.xlab.patient.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.patient.mvp.model.IChatRoomRecordsModel;

import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/3/17.
 */
public class ChatRoomRecordsModel extends PatientBaseModel<ChatRoomEntity> implements IChatRoomRecordsModel {

    private ChatRoomModelListener mChatRoomModelListener;

    private ChatRoomAPI mChatRoomAPI;

    public ChatRoomRecordsModel(ChatRoomModelListener chatRoomModelListener) {
        mChatRoomModelListener = chatRoomModelListener;
        mChatRoomAPI = mRetrofit.create(ChatRoomAPI.class);
    }

    @Override
    public void getChatRecords(String imGroupId, int page, int pageSize) {
        fetchData(mChatRoomAPI.getChatRecords(imGroupId, page, pageSize),true);
    }

    @Override
    protected void onSuccess(ChatRoomEntity response) {
        mChatRoomModelListener.onReceiveChatRecordSuccess(response.getRet_values());
    }

    @Override
    protected void onFailed(int retCode, String message) {
        mChatRoomModelListener.onReceiveFailed(message);
    }

    public interface ChatRoomModelListener extends BaseModelListener {
        void onReceiveChatRecordSuccess(ChatRoomEntity.RetValuesEntity valuesEntity);
    }
}
