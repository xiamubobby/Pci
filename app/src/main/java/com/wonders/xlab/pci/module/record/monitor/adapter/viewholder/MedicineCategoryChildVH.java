package com.wonders.xlab.pci.module.record.monitor.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.wonders.xlab.pci.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineCategoryChildVH extends ChildViewHolder {
    @Bind(R.id.tv_item_medicine_category_child_title)
    public TextView mTvTitle;
    @Bind(R.id.tv_item_medicine_category_child_value)
    public TextView mTvValue;
    @Bind(R.id.tv_item_medicine_category_child_counts)
    public TextView mTvCounts;
    
    public MedicineCategoryChildVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
