package com.wonders.xlab.pci.doctor.module.chatroom.prescription.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.PrescriptionItemBinding;
import com.wonders.xlab.pci.doctor.module.chatroom.prescription.adapter.bean.PrescriptionBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/4/26.
 */
public class PrescriptionRVAdapter extends SimpleRVAdapter<PrescriptionBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        PrescriptionBean bean = getBean(position);

        viewHolder.binding.setBean(bean);
        for (int i = 0; i < bean.medicineList.get().size(); i++) {
            TextView textView = new TextView(holder.itemView.getContext());
            textView.setText(bean.medicineList.get().get(i));

            viewHolder.mLlMedicineList.addView(textView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_prescription_time)
        TextView mTvTime;
        @Bind(R.id.tv_prescription_hospital_name)
        TextView mTvHospitalName;
        @Bind(R.id.ll_prescription_medicine_list)
        LinearLayout mLlMedicineList;
        PrescriptionItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = PrescriptionItemBinding.bind(itemView);
        }
    }
}
