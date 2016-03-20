package com.wonders.xlab.patient.module.main.doctors.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorMyItemBinding;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/3/14.
 */
public class MyDoctorRVAdapter extends SimpleRVAdapter<MyDoctorItemBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_my_item, parent, false);
        return new ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyDoctorItemBean dataItem = getBean(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setDoctorBean(dataItem);
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), viewHolder.mIvPortrait, dataItem.getPortraitUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    @Override
    public long getHeaderId(int position) {
        return getBean(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_my_item_header, parent, false);
        return new ItemHeaderViewHolder(headerView);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHeaderViewHolder viewHolder = (ItemHeaderViewHolder) holder;
        if (MyDoctorItemBean.TYPE_IN_SERVICE == getHeaderId(position)) {
            viewHolder.mTvDoctorMyItemHeader.setText("服务中");
        } else if (MyDoctorItemBean.TYPE_OUT_OF_SERVICE == getHeaderId(position)) {
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

        public ItemViewHolder(View itemView) {
            super(itemView);
            mIvPortrait = (ImageView) itemView.findViewById(R.id.iv_doctor_my_item_portrait);
            binding = DoctorMyItemBinding.bind(itemView);
        }
    }

}
