package com.wonders.xlab.pci.module.record.monitor.adapter;

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
import com.wonders.xlab.pci.module.record.monitor.bean.BPBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sjy on 2015/12/22.
 */
public class BPAdapter extends SimpleRVAdapter<BPBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTvItemBpRecordTime.setText(DateUtil.format(getBean(position).getRecordTime(),"HH:mm"));
        viewHolder.mTvItemBpRecordSpDp.setText(String.format("%s/%s",getBean(position).getSystolicPressure(),getBean(position).getDiastolicPressure()));
        viewHolder.mTvItemBpRecordHeartRate.setText(getBean(position).getHeartRate());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bp, parent, false);
        return new ItemViewHolder(itemView);

    }

    @Override
    public long getHeaderId(int position) {
        return getBean(position).getHeaderId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View titleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_time, parent, false);

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
        @Bind(R.id.tv_item_bp_record_time)
        TextView mTvItemBpRecordTime;
        @Bind(R.id.tv_item_bp_record_sp_dp)
        TextView mTvItemBpRecordSpDp;
        @Bind(R.id.tv_item_bp_record_heart_rate)
        TextView mTvItemBpRecordHeartRate;

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
