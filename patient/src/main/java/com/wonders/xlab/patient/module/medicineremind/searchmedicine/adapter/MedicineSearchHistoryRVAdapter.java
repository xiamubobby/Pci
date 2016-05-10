package com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.MedicineSearchHistoryItemBinding;
import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineSearchHistoryRVAdapter extends SimpleRVAdapter<MedicineRealmBean> {
    private OnRemoveClickListener mRemoveClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_search_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setBean(getBean(position));
        viewHolder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (null != mRemoveClickListener) {
                    mRemoveClickListener.onClick(getBean(adapterPosition).getId());
                }
                remove(adapterPosition);
            }
        });

    }

    public void setRemoveClickListener(OnRemoveClickListener removeClickListener) {
        mRemoveClickListener = removeClickListener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_medicine_search_history_item_delete)
        ImageView mIvDelete;

        MedicineSearchHistoryItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = MedicineSearchHistoryItemBinding.bind(itemView);
        }
    }

    public interface OnRemoveClickListener{
        void onClick(String id);
    }
}
