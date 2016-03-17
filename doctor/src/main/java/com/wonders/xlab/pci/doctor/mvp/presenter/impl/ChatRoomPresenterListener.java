package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;

import java.util.List;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public interface ChatRoomPresenterListener extends BasePresenterListener {
    void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

    void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);

    void sendMessageSuccess(long time);
}
