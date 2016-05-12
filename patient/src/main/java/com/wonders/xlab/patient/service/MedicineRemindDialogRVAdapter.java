package com.wonders.xlab.patient.service;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.data.realm.MedicationUsagesRealm;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/5/12.
 */
public class MedicineRemindDialogRVAdapter extends SimpleRVAdapter<MedicationUsagesRealm> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_remind_dialog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        MedicationUsagesRealm bean = getBean(position);
        viewHolder.mTvName.setText(bean.getMedicationName());
        viewHolder.mTvNumAndUnit.setText(String.format("%s%s", bean.getMedicationNum(), bean.getPharmaceuticalUnit()));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_medicine_remind_dialog_item_name)
        TextView mTvName;
        @Bind(R.id.tv_medicine_remind_dialog_item_num_and_unit)
        TextView mTvNumAndUnit;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
