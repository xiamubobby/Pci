package com.wonders.xlab.pci.doctor.module.chatroom.bean;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

/**
 * Created by hua on 16/2/19.
 */
public class MeChatRoomBean extends ChatRoomBean {
    public ObservableField<String> portraitUrl = new ObservableField<>();
    public ObservableField<String> recordTime = new ObservableField<>();
    public ObservableField<Long> recordTimeInMill = new ObservableField<>();
    public ObservableField<String> text = new ObservableField<>();
    public ObservableArrayList<String> imageUrls = new ObservableArrayList<>();
    public ObservableField<Boolean> isSending = new ObservableField<>();

    @Override
    public int getItemLayout() {
        return ITEM_LAYOUT_ME;
    }
}
