package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public interface IChatRoomPresenter extends IBasePresenter {
    void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

    void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);
}
