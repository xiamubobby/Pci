package com.wonders.xlab.pci.module.record.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.adapter.recyclerview.SimpleRVAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.bean.ReportDetailBean;

import java.lang.ref.WeakReference;

import butterknife.Bind;

/**
 * Created by hua on 15/12/14.
 */
public class ReportDetailRVAdapter extends SimpleRVAdapter<ReportDetailBean> {

    private WeakReference<Context> mContext;
    private LayoutInflater mInflater;

    public ReportDetailRVAdapter(WeakReference<Context> contextWeakReference) {
        mContext = contextWeakReference;
        mInflater = LayoutInflater.from(mContext.get());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_report_detail, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTvTitle.setText(getBean(position).getTitle());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_report_detail_title)
        TextView mTvTitle;
        @Bind(R.id.rv_item_report_detail)
        RecyclerView mRv;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
