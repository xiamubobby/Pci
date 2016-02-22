package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;

/**
 * Created by hua on 16/2/22.
 */
public interface IChatRoomModel {
    void onReceiveChatRoomHistorySuccess(ChatRoomEntity chatRoomEntity);

    void onReceiveChatRoomHistoryFailed(String message);
}
