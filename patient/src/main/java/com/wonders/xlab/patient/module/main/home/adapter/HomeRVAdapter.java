package com.wonders.xlab.patient.module.main.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.HomeRvItemBinding;
import com.wonders.xlab.patient.module.main.home.adapter.bean.HomeItemBean;

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
        viewHolder.binding.setHome(getBean(position));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        HomeRvItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = HomeRvItemBinding.bind(itemView);
        }
    }
}
