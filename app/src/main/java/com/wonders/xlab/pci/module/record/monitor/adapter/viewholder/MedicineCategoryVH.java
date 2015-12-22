package com.wonders.xlab.pci.module.record.monitor.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.wonders.xlab.pci.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineCategoryVH extends ParentViewHolder {
    @Bind(R.id.tv_item_medicine_category)
    public TextView mTvCategory;
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public MedicineCategoryVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
