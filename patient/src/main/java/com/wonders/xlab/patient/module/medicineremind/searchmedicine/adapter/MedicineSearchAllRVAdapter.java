package com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.MedicineSearchAllItemBinding;
import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineSearchAllRVAdapter extends SimpleRVAdapter<MedicineRealmBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_search_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((ItemViewHolder) holder).binding.setBean(getBean(position));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        MedicineSearchAllItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = MedicineSearchAllItemBinding.bind(itemView);
        }
    }

}
