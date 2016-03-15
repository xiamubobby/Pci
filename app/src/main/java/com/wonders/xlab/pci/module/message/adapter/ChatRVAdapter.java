package com.wonders.xlab.pci.module.message.adapter;

import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.multi.MultiRVAdapter;
import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
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
            case ChatBean.ITEM_MESSAGE:
                holder = new MessageViewHolder(itemView);
                break;
            case ChatBean.ITEM_NOTICE:
                holder = new NoticeViewHolder(itemView);
                break;
            default:
                throw new IllegalArgumentException("you must set your layout in ChatBean.ITEM_MESSAGE and ChatBean.ITEM_NOTICE as your viewType");
        }
        return holder;
    }

}
