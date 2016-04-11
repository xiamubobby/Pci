package com.wonders.xlab.patient.module.main.doctors.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.patient.util.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorDetailMemberItemBinding;
import com.wonders.xlab.patient.module.main.doctors.detail.adapter.bean.DoctorDetailGroupMemberBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/3/16.
 */
public class DoctorDetailMemberRVAdapter extends SimpleRVAdapter<DoctorDetailGroupMemberBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_detail_member_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        DoctorDetailGroupMemberBean bean = getBean(position);

        viewHolder.binding.setBean(bean);
        ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(),viewHolder.mIvDoctorPortrait,bean.portraitUrl.get(), ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_doctor_detail_member_item_portrait)
        ImageView mIvDoctorPortrait;

        DoctorDetailMemberItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = DoctorDetailMemberItemBinding.bind(itemView);
        }
    }
}
