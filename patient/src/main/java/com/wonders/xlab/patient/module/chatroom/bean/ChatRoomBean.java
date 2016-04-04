package com.wonders.xlab.patient.module.chatroom.bean;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.wonders.xlab.common.recyclerview.adapter.bean.BaseBean;
import com.wonders.xlab.patient.R;

/**
 * Created by hua on 16/2/19.
 */
public abstract class ChatRoomBean extends BaseBean {
    public final static int ITEM_LAYOUT_ME = R.layout.chat_room_item_me;
    public final static int ITEM_LAYOUT_OTHERS = R.layout.chat_room_item_others;

    public ObservableField<String> userId = new ObservableField<>();
    public ObservableField<String> messageId = new ObservableField<>();
    public ObservableField<String> portraitUrl = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> text = new ObservableField<>();
    public ObservableArrayList<String> imageUrls = new ObservableArrayList<>();
    public ObservableField<String> recordTimeInStr = new ObservableField<>();
    public ObservableField<Long> recordTimeInMill = new ObservableField<>();
    public ObservableField<Boolean> isSending = new ObservableField<>(false);
    /**
     * 是否已读
     */
    public ObservableField<Boolean> hasReaded = new ObservableField<>(false);
}
