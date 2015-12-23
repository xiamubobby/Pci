package com.wonders.xlab.pci.module.record.monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.module.record.monitor.mvn.entity.MedicineEntity;

import java.lang.ref.WeakReference;

/**
 * Created by hua on 15/12/22.
 */
public class MedicineRVAdapter extends SimpleRVAdapter<MedicineEntity> {
    public MedicineRVAdapter(WeakReference<Context> contextWeakReference) {
        super(contextWeakReference);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}