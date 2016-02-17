package com.wonders.xlab.pci.module.message.adapter;

import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.MultiRVAdapter;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.pci.module.message.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.message.viewholder.HistoryTaskViewHolder;
import com.wonders.xlab.pci.module.message.viewholder.TodayTaskViewHolder;

/**
 * Created by hua on 15/12/14.
 */
public class HomeRVAdapter extends MultiRVAdapter {

    @Override
    public MultiViewHolder createViewHolder(View itemView, int viewType) {
        MultiViewHolder holder;
        switch (viewType) {
            case HomeTaskBean.ITEM_HISTORY:
                holder = new HistoryTaskViewHolder(itemView);
                break;
            case HomeTaskBean.ITEM_TODAY:
                holder = new TodayTaskViewHolder(itemView);
                break;
            default:
                throw new IllegalArgumentException("you must set your layout in HomeTaskBean.ITEM_HISTORY and HomeTaskBean.ITEM_TODAY as your viewType");
        }
        return holder;
    }

}
