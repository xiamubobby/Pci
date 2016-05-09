package com.wonders.xlab.patient.module.medicineremind.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.MedicineRemindItemBinding;
import com.wonders.xlab.patient.module.medicineremind.list.bean.MedicineRemindBean;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setBean(getBean(position));
        viewHolder.mSwOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getBean(viewHolder.getAdapterPosition()).isChecked.set(isChecked);
            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sw_medicine_remind_item_on)
        Switch mSwOn;

        MedicineRemindItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = MedicineRemindItemBinding.bind(itemView);
        }
    }
}
