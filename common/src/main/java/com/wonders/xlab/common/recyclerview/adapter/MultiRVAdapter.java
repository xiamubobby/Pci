package com.wonders.xlab.common.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/10/23.
 */
public abstract class MultiRVAdapter<bean extends BaseBean> extends RecyclerView.Adapter<MultiViewHolder<bean>> {

    private List<bean> mDatas;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     * 设置数据，会先清除原来的数据
     *
     * @param beans
     */
    public void setDatas(List<bean> beans) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        } else {
            this.mDatas.clear();
        }
        this.mDatas.addAll(beans);

        notifyDataSetChanged();
    }

    /**
     * 将新数据添加到原来数据之后
     *
     * @param beans
     */
    public void appendDatas(List<bean> beans) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.mDatas.addAll(beans);

        notifyDataSetChanged();
    }

    /**
     * 添加一条新数据到原来数据之后
     *
     * @param bean
     */
    public void appendData(bean bean) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.mDatas.add(bean);

        notifyItemInserted(this.mDatas.size() - 1);
    }

    /**
     * 添加一条新数据到原来数据的 开始
     *
     * @param bean
     */
    public void addToTop(bean bean) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.mDatas.add(0, bean);

        notifyItemInserted(0);
    }

    public void addToPosition(bean bean, int position) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        if (this.mDatas.size() > position) {
            this.mDatas.add(position, bean);
        } else {
            appendData(bean);
        }
        notifyItemInserted(position);
    }

    public void clear() {
        if (this.mDatas != null) {
            this.mDatas.clear();
            notifyDataSetChanged();
        }
    }

    public bean getItemData(int position) {
        if (this.mDatas != null && this.mDatas.size() > position) {
            return this.mDatas.get(position);
        }
        return null;
    }

    @Override
    public MultiViewHolder<bean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return createViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final MultiViewHolder<bean> holder, final int position) {
        holder.onBindViewHolder(this.mDatas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mDatas == null ? 0 : this.mDatas.size();
    }


    @Override
    public int getItemViewType(int position) {
        return this.mDatas.get(position).getItemLayout();
    }

    public abstract MultiViewHolder<bean> createViewHolder(View itemView, int viewType);
}
