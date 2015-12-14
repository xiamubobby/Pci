package com.wonders.xlab.pci.module.home.adapter;

import android.content.Context;
import android.view.View;

import com.wonders.xlab.common.adapter.recyclerview.MultiRVAdapter;
import com.wonders.xlab.common.adapter.recyclerview.MultiViewHolder;
import com.wonders.xlab.pci.module.home.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.home.viewholder.TodayTaskViewHolder;
import com.wonders.xlab.pci.module.home.viewholder.YesterdayTaskViewHolder;

/**
 * Created by hua on 15/12/14.
 */
public class HomeRVAdapter extends MultiRVAdapter {
    public HomeRVAdapter(Context context) {
        super(context);
    }

    @Override
    public MultiViewHolder createViewHolder(View itemView, int viewType) {
        MultiViewHolder holder;
        switch (viewType) {
            case HomeTaskBean.ITEM_YESTERDAY:
                holder = new YesterdayTaskViewHolder(itemView);
                break;
            case HomeTaskBean.ITEM_TODAY:
                holder = new TodayTaskViewHolder(itemView);
                break;
            default:
                throw new IllegalArgumentException("you must set your layout in HomeTaskBean.ITEM_YESTERDAY and HomeTaskBean.ITEM_TODAY as your viewType");
        }
        return holder;
    }

}
