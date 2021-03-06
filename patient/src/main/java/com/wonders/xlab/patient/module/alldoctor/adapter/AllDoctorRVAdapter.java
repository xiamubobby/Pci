package com.wonders.xlab.patient.module.alldoctor.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorAllItemBinding;
import com.wonders.xlab.patient.util.ImageViewManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.avatarassemble.library.MultiAvatarView;

/**
 * Created by hua on 16/3/15.
 */
public class AllDoctorRVAdapter extends SimpleRVAdapter<AllDoctorItemBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_all_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        AllDoctorItemBean itemBean = getBean(position);
        viewHolder.binding.setBean(itemBean);
        viewHolder.mIvPortrait.setAvatarUrls(itemBean.getPortraitUrl());

        viewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(viewHolder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        viewHolder.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        PackageIconRVAdapter packageIconRVAdapter = new PackageIconRVAdapter();
        packageIconRVAdapter.setDatas(itemBean.getServiceIconUrl());
        viewHolder.mRecyclerView.setAdapter(packageIconRVAdapter);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_doctor_detail_portrait)
        MultiAvatarView mIvPortrait;
        @Bind(R.id.recycler_view_doctor_all_item_services)
        RecyclerView mRecyclerView;

        DoctorAllItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = DoctorAllItemBinding.bind(itemView);
        }
    }

    /**
     * 服务图标
     */
    class PackageIconRVAdapter extends SimpleRVAdapter<String> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_all_item_service_icon, parent, false);
            return new ServiceIconViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            ServiceIconViewHolder viewHolder = (ServiceIconViewHolder) holder;
            ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(), viewHolder.mIvDoctorAllItemServiceIcon, getBean(position), ImageViewManager.PLACE_HOLDER_EMPTY);
        }

        class ServiceIconViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_doctor_all_item_service_icon)
            ImageView mIvDoctorAllItemServiceIcon;

            public ServiceIconViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
