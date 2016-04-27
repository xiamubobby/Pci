package com.wonders.xlab.pci.doctor.module.chatroom.prescription.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
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
import im.hua.utils.DensityUtil;

/**
 * Created by hua on 16/4/26.
 */
public class PrescriptionRVAdapter extends SimpleRVAdapter<PrescriptionBean> {

    Context context;

    public PrescriptionRVAdapter(Context context) {
        this.context = context;
    }

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

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(context, 48.0f));
        for (int i = 0; i < bean.medicineList.get().size(); i++) {
            TextView textView = new TextView(holder.itemView.getContext());
            textView.setText(bean.medicineList.get().get(i));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(8, 0, 8, 0);
            textView.setSingleLine(true);
            viewHolder.mLlMedicineList.addView(textView, p);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_prescription_medicine_list)
        LinearLayout mLlMedicineList;
        PrescriptionItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = PrescriptionItemBinding.bind(itemView);
        }
    }
}
