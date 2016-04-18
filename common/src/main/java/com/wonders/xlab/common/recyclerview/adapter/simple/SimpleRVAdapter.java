package com.wonders.xlab.common.recyclerview.adapter.simple;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hua on 15/11/30.
 * 1、必须重写Bean的equal
 */
public abstract class SimpleRVAdapter<Bean> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bean> mBeanList;

    private OnClickListener mOnClickListener;

    private OnLongClickListener mItemLongClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public interface OnLongClickListener {
        void onItemLongClick(int position);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mItemLongClickListener) {
                    mItemLongClickListener.onItemLongClick(holder.getAdapterPosition());
                }
                return false;
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
        notifyItemInserted(mBeanList.size());
    }

    public void remove(int position) {
        if (mBeanList != null && mBeanList.size() > position) {
            mBeanList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        if (mBeanList != null) {
            int size = mBeanList.size();
            mBeanList.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    /**
     * 因为这里需要有移动的动画效果，所以采用了遍历的方式来交换位置
     *
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
        int size = this.mBeanList.size();
        this.mBeanList.addAll(mBeanList);
        notifyItemRangeInserted(size, mBeanList.size());
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
