package com.wonders.xlab.patient.mvp.presenter.impl;


import com.wonders.xlab.patient.module.chatroom.bean.ChatRoomBean;

import java.util.List;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public interface IChatRoomPresenter extends IBasePresenter {
    void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

    void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);

    void sendMessageSuccess(long time);
}
