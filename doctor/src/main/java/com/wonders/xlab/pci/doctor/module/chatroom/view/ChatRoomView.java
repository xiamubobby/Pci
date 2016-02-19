package com.wonders.xlab.pci.doctor.module.chatroom.view;

import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;

import java.util.List;

import im.hua.library.base.mvp.BaseView;

/**
 * Created by hua on 16/2/19.
 */
public interface ChatRoomView extends BaseView {
    void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

    void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);
}
