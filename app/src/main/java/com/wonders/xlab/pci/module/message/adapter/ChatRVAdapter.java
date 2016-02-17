package com.wonders.xlab.pci.module.message.adapter;

import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.MultiRVAdapter;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.pci.module.message.bean.ChatBean;
import com.wonders.xlab.pci.module.message.viewholder.MessageViewHolder;
import com.wonders.xlab.pci.module.message.viewholder.NoticeViewHolder;

/**
 * Created by hua on 15/12/14.
 */
public class ChatRVAdapter extends MultiRVAdapter<ChatBean> {

    @Override
    public MultiViewHolder<ChatBean> createViewHolder(View itemView, int viewType) {
        MultiViewHolder holder;
        switch (viewType) {
            case ChatBean.ITEM_HISTORY:
                holder = new MessageViewHolder(itemView);
                break;
            case ChatBean.ITEM_TODAY:
                holder = new NoticeViewHolder(itemView);
                break;
            default:
                throw new IllegalArgumentException("you must set your layout in ChatBean.ITEM_HISTORY and ChatBean.ITEM_TODAY as your viewType");
        }
        return holder;
    }

}
