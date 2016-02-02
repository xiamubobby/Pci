package com.wonders.xlab.pci.module.message.adapter;

import android.content.Context;
import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.MultiRVAdapter;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.pci.module.message.bean.HomeTaskBean;
import com.wonders.xlab.pci.module.message.viewholder.TodayTaskViewHolder;
import com.wonders.xlab.pci.module.message.viewholder.HistoryTaskViewHolder;

import java.lang.ref.WeakReference;

/**
 * Created by hua on 15/12/14.
 */
public class HomeRVAdapter extends MultiRVAdapter {
    private WeakReference<Context> mContextWeakReference;

    public HomeRVAdapter(WeakReference<Context> context) {
        super(context);
        mContextWeakReference = context;
    }

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
        holder.setContext(mContextWeakReference);
        return holder;
    }

}
