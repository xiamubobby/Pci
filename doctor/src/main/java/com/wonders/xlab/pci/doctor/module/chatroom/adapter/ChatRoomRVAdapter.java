package com.wonders.xlab.pci.doctor.module.chatroom.adapter;

import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.MultiRVAdapter;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.pci.doctor.module.chatroom.adapter.viewholder.MeChatRoomVH;
import com.wonders.xlab.pci.doctor.module.chatroom.adapter.viewholder.OthersChatRoomVH;
import com.wonders.xlab.pci.doctor.module.chatroom.bean.ChatRoomBean;

/**
 * Created by hua on 16/2/19.
 */
public class ChatRoomRVAdapter extends MultiRVAdapter<ChatRoomBean> {
    @Override
    public MultiViewHolder createViewHolder(View itemView, int viewType) {
        MultiViewHolder holder;
        switch (viewType) {
            case ChatRoomBean.ITEM_LAYOUT_ME:
                holder = new MeChatRoomVH(itemView);
                break;
            case ChatRoomBean.ITEM_LAYOUT_OTHERS:
                holder = new OthersChatRoomVH(itemView);
                break;
            default:
                throw new IllegalArgumentException("you must set your layout in ChatRoomBean.ITEM_LAYOUT_ME and ChatRoomBean.ITEM_LAYOUT_OTHERS as your viewType");

        }
        return holder;
    }
}
