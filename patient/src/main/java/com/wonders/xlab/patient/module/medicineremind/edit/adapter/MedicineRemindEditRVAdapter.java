package com.wonders.xlab.patient.module.medicineremind.edit.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineRemindEditRVAdapter extends SimpleRVAdapter<MedicineRealmBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_remind_edit_medicine_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        MedicineRealmBean bean = getBean(position);
        viewHolder.mTvName.setText(bean.getMedicineName());
        if (TextUtils.isEmpty(bean.getDose()) || bean.getDose().equals("0")) {
            viewHolder.mTvDose.setText(String.format("%s%s", "0", bean.getFormOfDrug()));
        } else {
            viewHolder.mTvDose.setText(String.format("%s%s", bean.getDose(), bean.getFormOfDrug()));
        }

        viewHolder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(viewHolder.getAdapterPosition());
            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_medicine_remind_edit_medicine_name)
        TextView mTvName;
        @Bind(R.id.tv_medicine_remind_edit_medicine_dose)
        TextView mTvDose;
        @Bind(R.id.iv_medicine_remind_edit_medicine_delete)
        ImageView mIvDelete;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
