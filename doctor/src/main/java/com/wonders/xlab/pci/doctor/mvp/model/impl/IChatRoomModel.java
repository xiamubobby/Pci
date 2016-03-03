package com.wonders.xlab.pci.doctor.mvp.model.impl;

import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;

import im.hua.library.base.mvp.impl.IBasePageModel;

/**
 * Created by hua on 16/2/22.
 */
public interface IChatRoomModel  extends IBasePageModel {
    void onReceiveChatRoomHistorySuccess(ChatRoomEntity chatRoomEntity, boolean shouldAppend);
}
