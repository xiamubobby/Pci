package com.wonders.xlab.pci.module.mydoctor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.mydoctor.mvn.DoctorInfoEntity;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/1/28.
 */
public class MyDoctorRVAdapter extends SimpleRVAdapter<DoctorInfoEntity> {

    public MyDoctorRVAdapter(WeakReference<Context> contextWeakReference) {
        super(contextWeakReference);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_my_doctor, parent, false);
        return new DoctorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        DoctorViewHolder viewHolder = (DoctorViewHolder) holder;

        DoctorInfoEntity doctorInfo = getBean(position);

        viewHolder.mTvMyDoctorName.setText(doctorInfo.getName());
        viewHolder.mTvMyDoctorHospital.setText(doctorInfo.getHospitalname());
        viewHolder.mTvMyDoctorDepartment.setText(doctorInfo.getParentdepartname());
        viewHolder.mTvMyDoctorJob.setText(doctorInfo.getTitle());
        viewHolder.mTvMyDoctorDescription.setText(doctorInfo.getDes());
        Glide.with(getContextWeakReference().get())
                .load(doctorInfo.getPic())
                .placeholder(R.drawable.user_avatar_default)
                .crossFade()
                .fitCenter()
                .into(viewHolder.mIvMyDoctorPortrait);
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_my_doctor_portrait)
        ImageView mIvMyDoctorPortrait;
        @Bind(R.id.tv_my_doctor_name)
        TextView mTvMyDoctorName;
        @Bind(R.id.tv_my_doctor_hospital)
        TextView mTvMyDoctorHospital;
        @Bind(R.id.tv_my_doctor_department)
        TextView mTvMyDoctorDepartment;
        @Bind(R.id.tv_my_doctor_job)
        TextView mTvMyDoctorJob;
        @Bind(R.id.tv_my_doctor_description)
        TextView mTvMyDoctorDescription;

        public DoctorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
