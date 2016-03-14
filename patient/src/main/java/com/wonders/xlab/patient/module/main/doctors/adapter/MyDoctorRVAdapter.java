package com.wonders.xlab.patient.module.main.doctors.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorMyItemBinding;
import com.wonders.xlab.patient.module.base.CustomUltimateViewAdapter;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/3/14.
 */
public class MyDoctorRVAdapter extends CustomUltimateViewAdapter<MyDoctorItemBean> {

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new ItemViewHolder(view, false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_my_item, parent, false);
        return new ItemViewHolder(headerView, true);
    }

    @Override
    public long generateHeaderId(int position) {
        return getDataItem(position).getHeaderId();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyDoctorItemBean dataItem = getDataItem(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        if (viewHolder.mIsItem) {
            viewHolder.binding.setDoctorBean(dataItem);
            ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), viewHolder.mIvPortrait, dataItem.getPortraitUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_my_item_header, parent, false);
        return new ItemHeaderViewHolder(headerView);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHeaderViewHolder viewHolder = (ItemHeaderViewHolder) holder;
        if (MyDoctorItemBean.HEADER_ID_IN_SERVICE == generateHeaderId(position)) {
            viewHolder.mTvDoctorMyItemHeader.setText("服务中");
        } else if (MyDoctorItemBean.HEADER_ID_OUT_OF_SERVICE == generateHeaderId(position)) {
            viewHolder.mTvDoctorMyItemHeader.setText("历史记录");
        }
    }

    class ItemHeaderViewHolder extends UltimateRecyclerviewViewHolder {
        @Bind(R.id.tv_doctor_my_item_header)
        TextView mTvDoctorMyItemHeader;

        public ItemHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends UltimateRecyclerviewViewHolder {
        ImageView mIvPortrait;

        DoctorMyItemBinding binding;

        boolean mIsItem;

        public ItemViewHolder(View itemView, boolean isItem) {
            super(itemView);
            this.mIsItem = isItem;
            if (isItem) {
                mIvPortrait = (ImageView) itemView.findViewById(R.id.iv_doctor_my_item_portrait);
                binding = DoctorMyItemBinding.bind(itemView);
            }
        }
    }

}
