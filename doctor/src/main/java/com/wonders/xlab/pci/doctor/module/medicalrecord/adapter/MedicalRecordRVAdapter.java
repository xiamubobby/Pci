package com.wonders.xlab.pci.doctor.module.medicalrecord.adapter;

import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.MultiRVAdapter;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.pci.doctor.module.medicalrecord.viewholder.MedicalRecordPhotoVH;

/**
 * Created by hua on 16/2/24.
 */
public class MedicalRecordRVAdapter extends MultiRVAdapter<MedicalRecordBean> {
    @Override
    public MultiViewHolder<MedicalRecordBean> createViewHolder(View itemView, int viewType) {
        MultiViewHolder viewHolder = null;
        switch (viewType) {
            case MedicalRecordBean.ITEM_LAYOUT_PHOTO:
                viewHolder = new MedicalRecordPhotoVH(itemView);
                break;
        }
        return viewHolder;
    }
}
