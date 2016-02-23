package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.pci.doctor.mvp.api.ChatRoomAPI;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IChatRoomModel;

import im.hua.library.base.mvp.BaseEntity;

/**
 * Created by hua on 16/2/19.
 */
public class ChatRoomModel extends DoctorBaseModel {

    private ChatRoomAPI mChatRoomAPI;

    private IChatRoomModel mChatRoomModelListener;

    public ChatRoomModel(IChatRoomModel callback) {
        mChatRoomModelListener = callback;
        mChatRoomAPI = mRetrofit.create(ChatRoomAPI.class);
    }

    public void getChatList() {
        fetchData(mChatRoomAPI.getChatHistory(), new ResponseListener() {
            @Override
            public void onSuccess(BaseEntity response) {
                if (mChatRoomModelListener != null) {
                    mChatRoomModelListener.onReceiveChatRoomHistorySuccess((ChatRoomEntity) response);
                }
            }

            @Override
            public void onFailed(Throwable e) {
                if (mChatRoomModelListener != null) {
                    mChatRoomModelListener.onReceiveFailed("");
                }
            }
        });
    }


}
