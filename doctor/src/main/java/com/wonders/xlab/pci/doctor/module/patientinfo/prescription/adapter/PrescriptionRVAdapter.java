package com.wonders.xlab.pci.doctor.module.patientinfo.prescription.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.patientinfo.prescription.adapter.bean.PrescriptionBean;

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
        viewHolder.hospitalName.setText("医院名称：" + bean.getHospitalName());
        viewHolder.itemDate.setText("开处方时间：" + bean.getRecordTime());
        viewHolder.mLlMedicineList.removeAllViews();
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(context, 48.0f));
        for (int i = 0; i < bean.medicineList.size(); i++) {
            TextView textView = new TextView(holder.itemView.getContext());
            textView.setText(bean.medicineList.get(i));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(8, 0, 8, 0);
            textView.setSingleLine(true);
            viewHolder.mLlMedicineList.addView(textView, p);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_prescription_medicine_list)
        LinearLayout mLlMedicineList;

        @Bind(R.id.prescription_item_date)
        TextView itemDate;
        @Bind(R.id.prescription_item_hospital_name)
        TextView hospitalName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
