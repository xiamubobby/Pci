package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.api.ChatRoomAPI;
import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ChatRoomModelListener;

/**
 * Created by hua on 16/2/19.
 */
public class ChatRoomModel extends DoctorBaseModel<ChatRoomEntity> {

    private ChatRoomAPI mChatRoomAPI;

    private ChatRoomModelListener mChatRoomModelListener;

    public ChatRoomModel(ChatRoomModelListener callback) {
        mChatRoomModelListener = callback;
        mChatRoomAPI = mRetrofit.create(ChatRoomAPI.class);
    }

    public void getChatList(String groupId) {
        if (!isLast()) {
            fetchData(mChatRoomAPI.getChatHistory(groupId, getPageIndex() + 1, getSize()), true);
        } else {
            mChatRoomModelListener.silenceRequest();
        }
    }

    @Override
    protected void onSuccess(ChatRoomEntity response) {
        if (mChatRoomModelListener != null) {
            ChatRoomEntity.RetValuesEntity ret_values = response.getRet_values();

            if (null == ret_values) {
                mChatRoomModelListener.onReceiveFailed("获取数据出错，请重试！");
                return;
            }

            setPageIndex(ret_values.getNumber());
            setLast(ret_values.isLast());
            setFirst(ret_values.isFirst());

            mChatRoomModelListener.onReceiveChatRoomHistorySuccess(response, getPageIndex() > 0);
        }
    }

    @Override
    protected void onFailed(Throwable e) {
        if (mChatRoomModelListener != null) {
            mChatRoomModelListener.onReceiveFailed("获取聊天记录失败，请重试！");
        }
    }
}
