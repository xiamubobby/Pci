package com.wonders.xlab.pci.module.record.userinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.databinding.ItemUserInfoBinding;
import com.wonders.xlab.pci.databinding.ItemUserInfoDividerBinding;

import java.lang.ref.WeakReference;

/**
 * Created by hua on 15/12/14.
 */
public class UserInfoRVAdapter extends SimpleRVAdapter<UserInfoBean> {
    public final static int VIEW_TYPE_ITEM = 0;
    public final static int VIEW_TYPE_DIVIDER = 1;

    private LayoutInflater mInflater;

    public UserInfoRVAdapter(WeakReference<Context> contextWeakReference) {
        super(contextWeakReference);
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
                viewHolder.binding.setUser(getBean(position));
                break;
            case VIEW_TYPE_DIVIDER:
                DividerViewHolder dividerViewHolder = (DividerViewHolder) holder;
                dividerViewHolder.binding.setLabel(getBean(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getBean(position).getViewType();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemUserInfoBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = ItemUserInfoBinding.bind(itemView);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        ItemUserInfoDividerBinding binding;

        public DividerViewHolder(View itemView) {
            super(itemView);
            binding = ItemUserInfoDividerBinding.bind(itemView);
        }
    }

}
