package com.wonders.xlab.pci.doctor.module.chatroom.model;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.base.DoctorBaseModel;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.view.ChatRoomView;
import com.wonders.xlab.pci.doctor.module.networkentity.ChatRoomEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 16/2/19.
 */
public class ChatRoomModel extends DoctorBaseModel<ChatRoomEntity> {

    private ChatRoomView mChatRoomView;

    public ChatRoomModel(ChatRoomView chatRoomView) {
        mChatRoomView = chatRoomView;
    }

    public void getChatList() {
        onSuccess(null);
    }

    @Override
    protected void onSuccess(ChatRoomEntity response) {
        List<ChatRoomBean> chatRoomBeanList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                MeChatRoomBean bean = new MeChatRoomBean();
                bean.name.set("我");
                bean.portraitUrl.set(Constant.DEFAULT_PORTRAIT);
                bean.recordTime.set("2016-02-03");
                bean.text.set("This is test message " + i);

                chatRoomBeanList.add(bean);
            } else {
                OthersChatRoomBean bean = new OthersChatRoomBean();
                bean.name.set("Doctor" + i);
                bean.portraitUrl.set(Constant.DEFAULT_PORTRAIT);
                bean.recordTime.set("2016-02-04");
                bean.text.set("This is test message " + i +" with 2 lines. \nLine 2 message.");

                chatRoomBeanList.add(bean);
            }
        }
        mChatRoomView.showChatMessageList(chatRoomBeanList);
    }

    @Override
    protected void onFailed(String message) {

    }
}
