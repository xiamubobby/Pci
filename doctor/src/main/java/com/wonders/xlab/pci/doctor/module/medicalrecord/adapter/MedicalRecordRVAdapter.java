package com.wonders.xlab.pci.doctor.module.medicalrecord.adapter;

import android.view.View;

import com.wonders.xlab.common.recyclerview.adapter.MultiRVAdapter;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordPhotoBean;
import com.wonders.xlab.pci.doctor.module.medicalrecord.viewholder.MedicalRecordPhotoVH;

import java.util.ArrayList;

/**
 * Created by hua on 16/2/24.
 */
public class MedicalRecordRVAdapter extends MultiRVAdapter<MedicalRecordBean> {
    private OnPhotoClickListener mOnPhotoClickListener;

    @Override
    public MultiViewHolder createViewHolder(View itemView, int viewType) {
        MultiViewHolder viewHolder = null;
        switch (viewType) {
            case MedicalRecordBean.ITEM_LAYOUT_PHOTO:
                viewHolder = new MedicalRecordPhotoVH(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MultiViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        switch (getItemViewType(position)) {
            case MedicalRecordBean.ITEM_LAYOUT_PHOTO:
                MedicalRecordPhotoVH viewHolder = (MedicalRecordPhotoVH) holder;
                viewHolder.setOnPhotoClickListener(new MedicalRecordPhotoVH.OnPhotoClickListener() {
                    @Override
                    public void onPhotoClick(int selectedPosition) {
                        if (null != mOnPhotoClickListener) {
                            mOnPhotoClickListener.onPhotoClick(((MedicalRecordPhotoBean) getItemData(holder.getAdapterPosition())).getPhotos(), selectedPosition);
                        }
                    }
                });
                break;
        }
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        mOnPhotoClickListener = onPhotoClickListener;
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(ArrayList<String> photoUrls, int selectedPosition);
    }
}
