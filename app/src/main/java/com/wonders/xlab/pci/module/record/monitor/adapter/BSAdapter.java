package com.wonders.xlab.pci.module.record.monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.monitor.bean.BSBean;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sjy on 2015/12/23.
 */
public class BSAdapter extends SimpleRVAdapter<BSBean> {
    private WeakReference<Context> mContext;
    private LayoutInflater mInflater;

    public BSAdapter(WeakReference<Context> contextWeakReference) {
        super(contextWeakReference);
        mContext = contextWeakReference;
        mInflater = LayoutInflater.from(mContext.get());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.item_bp,parent,false);
        return new ItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.bpDate.setText(getBean(position).toString());

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_bs_date)
        TextView bpDate;
        @Bind(R.id.tv_bs_suger)
        TextView bpRate;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
