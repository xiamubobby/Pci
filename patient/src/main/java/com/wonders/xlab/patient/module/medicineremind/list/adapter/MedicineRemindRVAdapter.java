package com.wonders.xlab.patient.module.medicineremind.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.MedicineRemindItemBinding;
import com.wonders.xlab.patient.module.medicineremind.list.bean.MedicineRemindBean;

/**
 * Created by wzh on 16/5/4.
 */
public class MedicineRemindRVAdapter extends SimpleRVAdapter<MedicineRemindBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_remind_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setBean(getBean(position));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        MedicineRemindItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = MedicineRemindItemBinding.bind(itemView);
        }
    }
}
