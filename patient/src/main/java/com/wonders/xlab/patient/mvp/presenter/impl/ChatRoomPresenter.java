package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.patient.mvp.model.IChatRoomRecordsModel;
import com.wonders.xlab.patient.mvp.model.impl.ChatRoomRecordsModel;
import com.wonders.xlab.patient.mvp.presenter.IChatRoomPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;


/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePresenter implements IChatRoomPresenter, ChatRoomRecordsModel.ChatRoomModelListener {

    private IChatRoomRecordsModel mChatRoomRecordsModel;

    private ChatRoomPresenterListener mChatRoomPresenterListener;

    public ChatRoomPresenter(ChatRoomPresenterListener presenter) {
        mChatRoomPresenterListener = presenter;

        mChatRoomRecordsModel = new ChatRoomRecordsModel(this);
        addModel(mChatRoomRecordsModel);
    }

    @Override
    public void getChatList(String groupId) {
        onReceiveChatRecordSuccess();
    }

    @Override
    public void onReceiveChatRecordSuccess() {
        List<ChatRoomBean> chatRoomBeanList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 5 == 0) {
                MeChatRoomBean bean = new MeChatRoomBean();
                bean.portraitUrl.set(Constant.DEFAULT_PORTRAIT);
                bean.recordTime.set(DateUtil.format(Calendar.getInstance().getTimeInMillis(), "yyyy-MM-dd HH:mm"));
                bean.text.set("this is message " + i);
                chatRoomBeanList.add(bean);
            } else {
                OthersChatRoomBean bean = new OthersChatRoomBean();
                bean.portraitUrl.set(Constant.DEFAULT_PORTRAIT);
                bean.recordTime.set(DateUtil.format(Calendar.getInstance().getTimeInMillis(), "yyyy-MM-dd HH:mm"));
                bean.text.set("this is message " + i);
                bean.name.set("刘" + i);
                chatRoomBeanList.add(bean);
            }
        }
        mChatRoomPresenterListener.showChatMessageList(chatRoomBeanList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mChatRoomPresenterListener.showError(message);
    }

    public interface ChatRoomPresenterListener extends BasePresenterListener {
        void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void sendMessageSuccess(long time);
    }
}
