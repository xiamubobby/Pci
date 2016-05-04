package com.wonders.xlab.patient.module.main.home.medicineremind.adapter;

import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.multi.MultiRVAdapter;
import com.wonders.xlab.common.recyclerview.adapter.multi.MultiViewHolder;
import com.wonders.xlab.patient.module.main.home.medicalrecord.viewholder.MedicalRecordPhotoVH;
import com.wonders.xlab.patient.module.main.home.medicineremind.bean.MedicineRemindBean;

/**
 * Created by wzh on 16/5/4.
 */
public class MedicineRemindRVAdapter extends MultiRVAdapter<MedicineRemindBean> {

    @Override
    public MultiViewHolder createViewHolder(View itemView, int viewType) {
        MultiViewHolder viewHolder = null;
        switch (viewType) {
            case MedicineRemindBean.ITEM_LAYOUT_MEDICINE_REMIND:
                viewHolder = new MedicalRecordPhotoVH(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MultiViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        switch (getItemViewType(position)) {
            case MedicineRemindBean.ITEM_LAYOUT_MEDICINE_REMIND:
                break;
        }
    }

}
