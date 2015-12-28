package com.wonders.xlab.pci.module.record.monitor.adapter.viewholder;

import android.view.View;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.wonders.xlab.pci.databinding.ItemMedicineCategoryChildBinding;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineCategoryChildVH extends ChildViewHolder {
    ItemMedicineCategoryChildBinding binding;

    public MedicineCategoryChildVH(View itemView) {
        super(itemView);
        binding = ItemMedicineCategoryChildBinding.bind(itemView);
    }

    public ItemMedicineCategoryChildBinding getBinding() {
        return binding;
    }
}
