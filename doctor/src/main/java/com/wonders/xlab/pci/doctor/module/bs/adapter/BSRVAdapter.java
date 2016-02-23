package com.wonders.xlab.pci.doctor.module.bs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.module.bs.bean.BSBean;

/**
 * Created by hua on 16/2/23.
 */
public class BSRVAdapter extends SimpleRVAdapter<BSBean> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

    }
}
