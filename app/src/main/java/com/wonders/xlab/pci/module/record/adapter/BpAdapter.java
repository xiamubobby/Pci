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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        Long time = getBean(position).getRecordTime();
        //int week =(int)(time - (4*24*3600000))%(7*24*3600000);
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        viewHolder.bpDate.setText(sdf.format(d));
        viewHolder.bpNum.setText(getBean(position).getSystolicPressure()+"/"+getBean(position).getDiastolicPressure());
        viewHolder.bpRate.setText(getBean(position).getHeartRate());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_bp,parent,false);
        return new ItemViewHolder(itemView);

    }

    @Override
    public long getHeaderId(int position) {
        Long time = getBean(position).getRecordTime();
        int week =(int)(time - (4*24*3600000))%(7*24*3600000);
        return week;
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
        Long time = getBean(position).getRecordTime();
        int week =(int)(time - (4*24*3600000))%(7*24*3600000);
            viewHolder.bpTitle.setText(week);
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
