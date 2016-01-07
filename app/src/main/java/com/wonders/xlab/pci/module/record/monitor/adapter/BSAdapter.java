package com.wonders.xlab.pci.module.record.monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.BuildConfig;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.monitor.bean.BSBean;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sjy on 2015/12/23.
 */
public class BSAdapter extends SimpleRVAdapter<BSBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private WeakReference<Context> mContext;
    private LayoutInflater mInflater;

    public BSAdapter(WeakReference<Context> contextWeakReference) {
        super(contextWeakReference);
        mContext = contextWeakReference;
        mInflater = LayoutInflater.from(mContext.get());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTvItemBsRecordTime.setText(DateUtil.format(getBean(position).getRecordTime(),"HH:mm"));
        viewHolder.mTvItemBsRecordBs.setText(getBean(position).getBs());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_bs, parent, false);
        return new ItemViewHolder(itemView);

    }

    @Override
    public long getHeaderId(int position) {
        return getBean(position).getHeaderId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View titleView = mInflater.inflate(R.layout.item_header_time, parent, false);

        return new TitleViewHolder(titleView);
    }

    /**
     * @param holder
     * @param position this position is not equal to the datas position
     */
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TitleViewHolder viewHolder = (TitleViewHolder) holder;
        long time = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (getBean(i).getHeaderId() == getHeaderId(position)) {
                time = getBean(i).getRecordTime();
                break;
            }
        }
        if (BuildConfig.DEBUG) Log.d("BPModel1", DateUtil.format(time, "yyyy-MM-dd"));
        viewHolder.bpTitle.setText(DateUtil.format(time, "yyyy-MM-dd"));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_bs_record_time)
        TextView mTvItemBsRecordTime;
        @Bind(R.id.tv_item_bs_record_bs)
        TextView mTvItemBsRecordBs;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_header_time)
        TextView bpTitle;

        public TitleViewHolder(View titleView) {
            super(titleView);
            ButterKnife.bind(this, titleView);
        }
    }
}
