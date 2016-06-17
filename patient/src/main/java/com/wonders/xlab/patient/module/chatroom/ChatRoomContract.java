package com.wonders.xlab.patient.module.chatroom;

import com.wonders.xlab.patient.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.patient.mvp.entity.ChatRoomEntity;

import java.util.List;
import java.util.Map;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/6/16.
 */
public interface ChatRoomContract {

    interface Callback extends BaseModelListener {
        void onReceiveChatRecordSuccess(ChatRoomEntity.RetValuesEntity entity);

        void onSendMessageSuccess(long time);
    }

    interface Model extends IBaseModel {
        void getChatRecords(String imGroupId, int page, int pageSize, Callback callback);

        void sendMessage(Map<String, Object> ext, long time, Callback callback);
    }

    interface ViewListener extends BasePresenterListener {
        void showChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void appendChatMessageList(List<ChatRoomBean> chatRoomBeanList);

        void sendMessageSuccess(long time);

        void showReachTheLastPageNotice(String message);
    }


    interface Presenter extends IBasePresenter {
        void getChatList(String groupId, boolean isRefresh);

        void sendMessage(Map<String, Object> ext, long time);
    }

}
