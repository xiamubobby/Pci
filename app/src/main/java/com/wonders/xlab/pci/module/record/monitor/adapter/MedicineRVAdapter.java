package com.wonders.xlab.pci.module.record.monitor.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import im.hua.utils.DateUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.monitor.adapter.viewholder.MedicineCategoryChildVH;
import com.wonders.xlab.pci.module.record.monitor.adapter.viewholder.MedicineCategoryVH;
import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryBean;
import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryChildBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineRVAdapter extends ExpandableRecyclerAdapter<MedicineCategoryVH, MedicineCategoryChildVH> {

    /**
     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
     * <p/>
     * Changes to {@link #mParentItemList} should be made through add/remove methods in
     * {@link ExpandableRecyclerAdapter}
     *
     * @param parentItemList List of all {@link ParentListItem} objects to be
     *                       displayed in the RecyclerView that this
     *                       adapter is linked to
     */
    public MedicineRVAdapter(@NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
    }

    public void setDatas(@NonNull List<? extends ParentListItem> parentItemList) {
        List datas = getParentItemList();
        if (datas == null) {
            datas = new ArrayList<>();
        } else {
            datas.clear();
        }
        datas.addAll(parentItemList);
        notifyDataSetChanged();
    }

    @Override
    public MedicineCategoryVH onCreateParentViewHolder(ViewGroup parentViewGroup) {
        return new MedicineCategoryVH(LayoutInflater.from(parentViewGroup.getContext()).inflate(R.layout.item_medicine_category, parentViewGroup, false));

    }

    @Override
    public MedicineCategoryChildVH onCreateChildViewHolder(ViewGroup childViewGroup) {
        return new MedicineCategoryChildVH(LayoutInflater.from(childViewGroup.getContext()).inflate(R.layout.item_medicine_category_child, childViewGroup, false));
    }

    @Override
    public void onBindParentViewHolder(MedicineCategoryVH parentViewHolder, int position, ParentListItem parentListItem) {
        MedicineCategoryBean bean = (MedicineCategoryBean) parentListItem;
        parentViewHolder.mTvTitle.setText(bean.getTitle());
    }

    @Override
    public void onBindChildViewHolder(MedicineCategoryChildVH childViewHolder, int position, Object childListItem) {
        MedicineCategoryChildBean bean = (MedicineCategoryChildBean) childListItem;
        childViewHolder.mTvTitle.setText(bean.isTitle() ? "时间" : DateUtil.format(bean.getTime(), DateUtil.DEFAULT_FORMAT));
        childViewHolder.mTvValue.setText(bean.isTitle() ? "剂量(mg)" : bean.getValue());
        childViewHolder.mTvCounts.setText(bean.isTitle() ? "次数(mg/天)" : bean.getCounts());
    }
}