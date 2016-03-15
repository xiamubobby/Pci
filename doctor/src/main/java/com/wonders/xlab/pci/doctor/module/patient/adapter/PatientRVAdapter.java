package com.wonders.xlab.pci.doctor.module.patient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.PatientItemBinding;
import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/2/19.
 */
public class PatientRVAdapter extends SimpleRVAdapter<PatientBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PatientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        PatientViewHolder viewHolder = (PatientViewHolder) holder;
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), viewHolder.mIvPatientItemPortrait, getBean(position).getPortrait(), xlab.wonders.com.common.R.drawable.portrait_default);

        viewHolder.mBinding.setPatient(getBean(position));
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_patient_item_portrait)
        ImageView mIvPatientItemPortrait;

        PatientItemBinding mBinding;

        public PatientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBinding = PatientItemBinding.bind(itemView);
        }

    }
}
