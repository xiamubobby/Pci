package com.wonders.xlab.patient.module.doctors.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorDetailPackageItemBinding;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailPackageBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorDetailPackageRVAdapter extends SimpleRVAdapter<DoctorDetailPackageBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_detail_package_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setBean(getBean(position));
        ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(), viewHolder.mIvPackageIcon, getBean(position).iconUrl.get(), ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_doctor_detail_package_item_icon)
        ImageView mIvPackageIcon;

        DoctorDetailPackageItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = DoctorDetailPackageItemBinding.bind(itemView);
        }
    }

}
