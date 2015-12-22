package com.wonders.xlab.pci.module.record.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.bean.BpBean;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sjy on 2015/12/22.
 */
public class BpAdapter  extends SimpleRVAdapter<BpBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>{
    private WeakReference<Context> mContext;
    private LayoutInflater mInflater;

    public BpAdapter(WeakReference<Context> contextWeakReference){
            super(contextWeakReference);
             mContext = contextWeakReference;
            mInflater = LayoutInflater.from(mContext.get());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.bpDate.setText("");
        viewHolder.bpNum.setText("");
        viewHolder.bpNum.setText("");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =mInflater.inflate(R.layout.content_bp,parent,false);
        return new ItemViewHolder(itemView);

    }

    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View titleView = mInflater.inflate(R.layout.item_bp_title,parent,false);

        return new TitleViewHolder(titleView);
    }
    class TitleViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_bp_title)
        TextView bpTitle;
        public TitleViewHolder(View titleView) {
            super(titleView);
            ButterKnife.bind(this, titleView);
        }
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TitleViewHolder viewHolder = (TitleViewHolder) holder;
            viewHolder.bpTitle.setText("");
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_bp_date)
        TextView bpDate;
        @Bind(R.id.tv_bp_num)
        TextView bpNum;
        @Bind(R.id.tv_bp_rate)
        TextView bpRate;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
