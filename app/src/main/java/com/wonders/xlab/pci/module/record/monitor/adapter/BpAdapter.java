package com.wonders.xlab.pci.module.record.monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.monitor.bean.BpBean;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sjy on 2015/12/22.
 */
public class BpAdapter extends SimpleRVAdapter<BpBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private WeakReference<Context> mContext;
    private LayoutInflater mInflater;

    public BpAdapter(WeakReference<Context> contextWeakReference) {
        super(contextWeakReference);
        mContext = contextWeakReference;
        mInflater = LayoutInflater.from(mContext.get());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        long time = getBean(position).getRecordTime();
        viewHolder.bpDate.setText(DateUtil.format(time, "HH:mm"));
        viewHolder.bpNum.setText(String.format("%s/%s", getBean(position).getSystolicPressure(), getBean(position).getDiastolicPressure()));
        viewHolder.bpRate.setText(getBean(position).getHeartRate());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_bp, parent, false);
        return new ItemViewHolder(itemView);

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public long getHeaderId(int position) {

        return getBean(position).getHeaderId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View titleView = mInflater.inflate(R.layout.item_bp_title, parent, false);

        return new TitleViewHolder(titleView);
    }

    /**
     * @param holder
     * @param position this position don't equal to the datas position
     */
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TitleViewHolder viewHolder = (TitleViewHolder) holder;
        long time = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (position == getHeaderId(i)) {
                time = getBean(i).getRecordTime();
                break;
            }
        }
        viewHolder.bpTitle.setText(DateUtil.format(time, "yyyy-MM-dd"));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_bp_hour)
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

    class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_bp_title)
        TextView bpTitle;

        public TitleViewHolder(View titleView) {
            super(titleView);
            ButterKnife.bind(this, titleView);
        }
    }
}
