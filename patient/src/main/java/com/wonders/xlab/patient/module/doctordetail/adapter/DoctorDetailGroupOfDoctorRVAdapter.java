package com.wonders.xlab.patient.module.doctordetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorDetailGroupOfDoctorItemBinding;
import com.wonders.xlab.patient.module.doctordetail.adapter.bean.DoctorDetailGroupOfDoctorBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.avatarassemble.library.MultiAvatarView;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorDetailGroupOfDoctorRVAdapter extends SimpleRVAdapter<DoctorDetailGroupOfDoctorBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_detail_group_of_doctor_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        DoctorDetailGroupOfDoctorBean bean = getBean(position);

        viewHolder.binding.setBean(bean);
        viewHolder.mIvGroupPortrait.setAvatarUrls(bean.groupPortraitUrls.get());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_doctor_detail_group_of_doctor_item_portrait)
        MultiAvatarView mIvGroupPortrait;

        DoctorDetailGroupOfDoctorItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = DoctorDetailGroupOfDoctorItemBinding.bind(itemView);
        }
    }
}
