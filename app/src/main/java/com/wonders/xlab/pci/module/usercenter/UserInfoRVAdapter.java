package com.wonders.xlab.pci.module.usercenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.R;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/14.
 */
public class UserInfoRVAdapter extends SimpleRVAdapter<UserInfoBean> {
    public final static int VIEW_TYPE_ITEM = 0;
    public final static int VIEW_TYPE_DIVIDER = 1;


    private LayoutInflater mInflater;

    public UserInfoRVAdapter(WeakReference<Context> contextWeakReference) {
        super();
        mInflater = LayoutInflater.from(contextWeakReference.get());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                viewHolder = new ItemViewHolder(mInflater.inflate(R.layout.item_user_info, parent, false));
                break;
            case VIEW_TYPE_DIVIDER:
                viewHolder = new DividerViewHolder(mInflater.inflate(R.layout.item_user_info_divider, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                viewHolder.mTvItemUserInfoLabel.setText(getBean(position).getLabel());
                viewHolder.mTvItemUserInfoValue.setText(getBean(position).getValue());
                break;
            case VIEW_TYPE_DIVIDER:
                DividerViewHolder dividerViewHolder = (DividerViewHolder) holder;
                dividerViewHolder.mTvUserInfoDividerLabel.setText(getBean(position).getLabel());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getBean(position).getViewType();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_user_info_label)
        TextView mTvItemUserInfoLabel;
        @Bind(R.id.tv_item_user_info_value)
        TextView mTvItemUserInfoValue;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_user_info_divider_label)
        TextView mTvUserInfoDividerLabel;

        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
