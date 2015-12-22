package com.wonders.xlab.common.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hua on 15/11/30.
 */
public abstract class SimpleRVAdapter<Bean> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private WeakReference<Context> mContextWeakReference;
    protected LayoutInflater mInflater;

    private List<Bean> mBeanList;

    private OnItemClickListener mOnItemClickListener;

    public SimpleRVAdapter(WeakReference<Context> contextWeakReference) {
        mContextWeakReference = contextWeakReference;
        this.mInflater = LayoutInflater.from(contextWeakReference.get());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public WeakReference<Context> getContextWeakReference() {
        return mContextWeakReference;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 0 : mBeanList.size();
    }

    public void insertToFist(Bean bean) {
        if (bean == null) {
            return;
        }
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        }
        mBeanList.add(0, bean);
        notifyItemInserted(0);
    }

    public void appendToLast(Bean bean) {
        if (bean == null) {
            return;
        }
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        }
        mBeanList.add(bean);
        notifyItemInserted(mBeanList.size() - 1);
    }

    public void remove(int position) {
        if (mBeanList != null && mBeanList.size() > position) {
            mBeanList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        if (mBeanList != null ) {
            mBeanList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 因为这里需要有移动的动画效果，所以采用了遍历的方式来交换位置
     * @param fromPosition
     * @param toPosition
     */
    public void swap(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(this.mBeanList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(this.mBeanList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setDatas(List<Bean> mBeanList) {
        if (mBeanList == null) {
            return;
        }
        if (this.mBeanList == null) {
            this.mBeanList = new ArrayList<>();
        } else {
            this.mBeanList.clear();
        }
        this.mBeanList.addAll(mBeanList);
        notifyDataSetChanged();
    }

    public void appendDatas(List<Bean> mBeanList) {
        if (mBeanList == null) {
            return;
        }
        if (this.mBeanList == null) {
            this.mBeanList = new ArrayList<>();
        }
        this.mBeanList.addAll(mBeanList);
        notifyDataSetChanged();
    }

    public List<Bean> getBeanList() {
        if (mBeanList == null) {
            mBeanList = new ArrayList<>();
        }
        return mBeanList;
    }

    public Bean getBean(int position) {
        if (mBeanList != null && mBeanList.size() > position) {
            return mBeanList.get(position);
        }
        return null;

    }
}
