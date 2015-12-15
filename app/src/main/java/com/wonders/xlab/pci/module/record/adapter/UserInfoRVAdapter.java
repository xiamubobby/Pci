package com.wonders.xlab.pci.module.record.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.adapter.recyclerview.SimpleRVAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.bean.UserInfoBean;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/14.
 */
public class UserInfoRVAdapter extends SimpleRVAdapter<UserInfoBean> {
    private LayoutInflater mInflater;

    public UserInfoRVAdapter(WeakReference<Context> contextWeakReference) {
        mInflater = LayoutInflater.from(contextWeakReference.get());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_info, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTvItemUserInfoLabel.setText(getBean(position).getLabel());
        viewHolder.mTvItemUserInfoValue.setText(getBean(position).getValue());
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

}
