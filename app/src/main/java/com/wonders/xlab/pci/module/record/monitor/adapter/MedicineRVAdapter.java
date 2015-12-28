package com.wonders.xlab.pci.module.record.monitor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.monitor.adapter.viewholder.MedicineCategoryChildVH;
import com.wonders.xlab.pci.module.record.monitor.adapter.viewholder.MedicineCategoryVH;
import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryBean;
import com.wonders.xlab.pci.module.record.monitor.bean.MedicineCategoryChildBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineRVAdapter extends ExpandableRecyclerAdapter<MedicineCategoryVH, MedicineCategoryChildVH> {

    private LayoutInflater mInflater;

    /**
     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
     * <p>
     * Changes to {@link #mParentItemList} should be made through add/remove methods in
     * {@link ExpandableRecyclerAdapter}
     *
     * @param parentItemList List of all {@link ParentListItem} objects to be
     *                       displayed in the RecyclerView that this
     *                       adapter is linked to
     */
    public MedicineRVAdapter(WeakReference<Context> contextWeakReference, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflater = LayoutInflater.from(contextWeakReference.get());
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
        if (mInflater != null) {
            return new MedicineCategoryVH(mInflater.inflate(R.layout.item_medicine_category, parentViewGroup, false));
        }
        return null;

    }

    @Override
    public MedicineCategoryChildVH onCreateChildViewHolder(ViewGroup childViewGroup) {
        if (mInflater != null) {
            return new MedicineCategoryChildVH(mInflater.inflate(R.layout.item_medicine_category_child, childViewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindParentViewHolder(MedicineCategoryVH parentViewHolder, int position, ParentListItem parentListItem) {
        MedicineCategoryBean bean = (MedicineCategoryBean) parentListItem;
        parentViewHolder.getBinding().setCategory(bean);
    }

    @Override
    public void onBindChildViewHolder(MedicineCategoryChildVH childViewHolder, int position, Object childListItem) {
        MedicineCategoryChildBean bean = (MedicineCategoryChildBean) childListItem;
        childViewHolder.getBinding().setMedicine(bean);
    }
}