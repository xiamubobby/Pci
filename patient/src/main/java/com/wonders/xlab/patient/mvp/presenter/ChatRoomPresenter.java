package com.wonders.xlab.patient.mvp.presenter;

import android.support.annotation.NonNull;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.chatroom.bean.ChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.MeChatRoomBean;
import com.wonders.xlab.patient.module.chatroom.bean.OthersChatRoomBean;
import com.wonders.xlab.patient.mvp.presenter.impl.IChatRoomPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;
import im.hua.utils.DateUtil;


/**
 * Created by hua on 16/2/22.
 */
public class ChatRoomPresenter extends BasePresenter {

    private String mDoctorId = "";
    private final String TYPE_USER = "User";
    private final String TYPE_DOCTOR = "Doctor";


    private IChatRoomPresenter mIChatRoomPresenter;

    public ChatRoomPresenter(IChatRoomPresenter presenter, @NonNull String doctorId) {
        mDoctorId = doctorId;
        mIChatRoomPresenter = presenter;
    }

    public void getChatList(String groupId) {
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
        mIChatRoomPresenter.showChatMessageList(chatRoomBeanList);
    }
}
