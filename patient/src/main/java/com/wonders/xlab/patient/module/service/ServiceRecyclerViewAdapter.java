package com.wonders.xlab.patient.module.service;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.ServiceCellBinding;
import com.wonders.xlab.patient.util.ImageViewManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natsuki on 16/5/6.
 */
public class ServiceRecyclerViewAdapter extends SimpleRVAdapter<ServiceListCellDataUnit> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_cell, parent, false);
        ServiceCellBinding binding = ServiceCellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        //ServiceListViewHolder vh = new ServiceListViewHolder(binding.getRoot());
        ServiceListViewHolder vh = new ServiceListViewHolder(v);
        //vh.binding = binding;
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ServiceListViewHolder viewHolder = (ServiceListViewHolder) holder;
        ServiceCellBinding binding = viewHolder.binding;
        binding.setData(getBean(position));
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), binding.portrait, getBean(position).getImgURL(), ImageViewManager.PLACE_HOLDER_EMPTY);
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), binding.somepic, getBean(position).getProviderImgURL(), ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    private class ServiceListViewHolder extends RecyclerView.ViewHolder {

        ServiceCellBinding binding;

        public ServiceListViewHolder(View itemView) {
            super(itemView);
            binding = ServiceCellBinding.bind(itemView);
        }

    }
}
