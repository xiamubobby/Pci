package com.wonders.xlab.common.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by hua on 15/10/23.
 */
public abstract class MultiViewHolder<bean extends BaseBean> extends RecyclerView.ViewHolder {

    private WeakReference<Context> mContextWeakReference;

    public MultiViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(bean data);

    public void setContext(WeakReference<Context> contextWeakReference) {
        mContextWeakReference = contextWeakReference;
    }

    public WeakReference<Context> getContextWeakReference() {
        return mContextWeakReference;
    }
}
