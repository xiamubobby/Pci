package com.wonders.xlab.common.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hua on 15/10/23.
 */
public abstract class MultiViewHolder<bean extends BaseBean> extends RecyclerView.ViewHolder {

    public MultiViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(bean data);
}
