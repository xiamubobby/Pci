package com.wonders.xlab.patient.module.medicineremind.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.MedicineRemindItemBinding;
import com.wonders.xlab.patient.module.medicineremind.list.bean.MedicineRemindBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 */
public class MedicineRemindRVAdapter extends SimpleRVAdapter<MedicineRemindBean> {

    private OnSwitchChangeListener mSwitchChangeListener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_remind_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        MedicineRemindBean bean = getBean(position);
        viewHolder.binding.setBean(bean);
        viewHolder.mSwOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = viewHolder.mSwOn.isChecked();
                getBean(viewHolder.getAdapterPosition()).shouldAlarm.set(isChecked);
                if (null != mSwitchChangeListener) {
                    mSwitchChangeListener.onSwitchStateChange(viewHolder.getAdapterPosition());
                }
            }
        });
    }

    public void setSwitchChangeListener(OnSwitchChangeListener switchChangeListener) {
        mSwitchChangeListener = switchChangeListener;
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

    public interface OnSwitchChangeListener{
        void onSwitchStateChange(int position);
    }
}
