package com.wonders.xlab.pci.doctor.module.me.notification.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.NotifiOthersItemBinding;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiOthersBean;

import butterknife.ButterKnife;

/**
 * Created by hua on 16/4/18.
 */
public class NotifiOthersRVAdapter extends SimpleRVAdapter<NotifiOthersBean> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notifi_others_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setBean(getBean(position));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        NotifiOthersItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = NotifiOthersItemBinding.bind(itemView);
        }
    }
}
