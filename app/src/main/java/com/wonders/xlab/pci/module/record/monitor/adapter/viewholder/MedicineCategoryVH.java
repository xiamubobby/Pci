package com.wonders.xlab.pci.module.record.monitor.adapter.viewholder;

import android.view.View;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.wonders.xlab.pci.databinding.ItemMedicineCategoryBinding;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineCategoryVH extends ParentViewHolder {
    ItemMedicineCategoryBinding binding;

    public MedicineCategoryVH(View itemView) {
        super(itemView);
        binding = ItemMedicineCategoryBinding.bind(itemView);
    }

    public ItemMedicineCategoryBinding getBinding() {
        return binding;
    }
}
