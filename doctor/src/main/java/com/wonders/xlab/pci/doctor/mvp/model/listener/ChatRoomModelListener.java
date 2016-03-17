package com.wonders.xlab.pci.doctor.mvp.model.listener;

import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;

import im.hua.library.base.mvp.listener.BasePageModelListener;

/**
 * Created by hua on 16/2/22.
 */
public interface ChatRoomModelListener extends BasePageModelListener {
    void onReceiveChatRoomHistorySuccess(ChatRoomEntity chatRoomEntity, boolean shouldAppend);
}
