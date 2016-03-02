package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.ChatRoomAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IChatRoomModel;

/**
 * Created by hua on 16/2/19.
 */
public class ChatRoomModel extends DoctorBaseModel<ChatRoomEntity> {

    private ChatRoomAPI mChatRoomAPI;

    private IChatRoomModel mChatRoomModelListener;

    public ChatRoomModel(IChatRoomModel callback) {
        mChatRoomModelListener = callback;
        mChatRoomAPI = mRetrofit.create(ChatRoomAPI.class);
    }

    public void getChatList(String groupId, int page, int size) {
        fetchData(mChatRoomAPI.getChatHistory(groupId, page, size), true);
    }

    @Override
    protected void onSuccess(ChatRoomEntity response) {
        if (mChatRoomModelListener != null) {
            mChatRoomModelListener.onReceiveChatRoomHistorySuccess(response);
        }
    }

    @Override
    protected void onFailed(Throwable e) {
        if (mChatRoomModelListener != null) {
            mChatRoomModelListener.onReceiveFailed("获取聊天记录失败，请重试！");
        }
    }
}
