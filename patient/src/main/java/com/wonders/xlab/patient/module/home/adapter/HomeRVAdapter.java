package com.wonders.xlab.patient.module.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.HomeRvItemBinding;
import com.wonders.xlab.patient.module.home.adapter.bean.HomeItemBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/3/8.
 */
public class HomeRVAdapter extends SimpleRVAdapter<HomeItemBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        HomeItemBean bean = getBean(position);
        viewHolder.binding.setHome(bean);
        viewHolder.mIvHomeRvItem.setImageResource(bean.getDrawableResId());
        viewHolder.mLlHomeRvItem.setBackgroundDrawable(viewHolder.itemView.getContext().getResources().getDrawable(bean.getBackgroundDrawableId()));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_home_rv_item)
        LinearLayout mLlHomeRvItem;

        @Bind(R.id.iv_home_rv_item)
        ImageView mIvHomeRvItem;

        HomeRvItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = HomeRvItemBinding.bind(itemView);
        }
    }
}
