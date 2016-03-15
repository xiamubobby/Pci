package com.wonders.xlab.common.recyclerview.adapter.multi;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.bean.BaseBean;

/**
 * Created by hua on 15/10/23.
 */
public abstract class MultiViewHolder<bean extends BaseBean> extends RecyclerView.ViewHolder {

    public MultiViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(bean data);
}
