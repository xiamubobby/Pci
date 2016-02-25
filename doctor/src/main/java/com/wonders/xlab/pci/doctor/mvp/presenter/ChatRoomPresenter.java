package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.pci.doctor.mvp.model.ChatRoomModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IChatRoomModel;
import com.wonders.xlab.pci.doctor.mvp.entity.ChatRoomEntity;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IChatRoomPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePresenter implements IChatRoomModel {

    private ChatRoomModel mChatRoomModel;
    private IChatRoomPresenter mIChatRoomView;

    public ChatRoomPresenter(IChatRoomPresenter presenter) {
        mIChatRoomView = presenter;
        mChatRoomModel = new ChatRoomModel(this);
        addModel(mChatRoomModel);
    }

    public void getChatList(String groupId) {
        mChatRoomModel.getChatList(groupId);
    }

    @Override
    public void onReceiveChatRoomHistorySuccess(ChatRoomEntity chatRoomEntity) {
        List<ChatRoomBean> chatRoomBeanList = new ArrayList<>();
        if (null == chatRoomEntity.getRet_values()) {

            return;
        }
        for (int i = 0; i < chatRoomEntity.getRet_values().size(); i++) {
            ChatRoomEntity.RetValuesEntity roomEntity = chatRoomEntity.getRet_values().get(i);

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
        if (mIChatRoomView != null) {
            mIChatRoomView.showChatMessageList(chatRoomBeanList);
        }

    }

    public void setIChatRoomView(IChatRoomPresenter IChatRoomView) {
        mIChatRoomView = IChatRoomView;
    }

    @Override
    public void onReceiveFailed(String message) {
        mIChatRoomView.showError(message);
    }
}
