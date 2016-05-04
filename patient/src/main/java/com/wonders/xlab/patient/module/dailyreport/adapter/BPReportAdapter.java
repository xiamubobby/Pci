package com.wonders.xlab.patient.module.dailyreport.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/3/20.
 */
public class BPReportAdapter extends SimpleRVAdapter<BPReportBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bp_report_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        BPReportBean bean = getBean(position);
        viewHolder.mTvHigh.setText(String.format("高压：%smmHg", bean.getHighPressure()));
        viewHolder.mTvLow.setText(String.format("低压：%smmHg", bean.getLowPressure()));
        viewHolder.mTvHeartRate.setText(String.format("心率：%s次/分", bean.getHeartRate()));
        viewHolder.mTvTime.setText(String.format("记录时间：%s", DateUtil.format(bean.getRecordTimeInMill(), "HH:mm")));
        viewHolder.mTvHighRange.setText(bean.getHighPressureRange());
        viewHolder.mTvLowRange.setText(bean.getLowPressureRange());
        viewHolder.mTvHeartRateRange.setText(bean.getHeartRateRange());

        Drawable drawableUp = viewHolder.itemView.getContext().getResources().getDrawable(R.drawable.pic_arrow_up);
        if (drawableUp != null) {
            drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        }
        Drawable drawableDown = viewHolder.itemView.getContext().getResources().getDrawable(R.drawable.pic_arrow_down);
        if (drawableDown != null) {
            drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
        }

        switch (bean.getHighPressureStatus()) {
            case BPReportBean.STATUS_HIGH:
                viewHolder.mTvHigh.setCompoundDrawables(drawableUp, null, null, null);
                break;
            case BPReportBean.STATUS_NORMAL:
                viewHolder.mTvHigh.setCompoundDrawables(null, null, null, null);
                break;
            case BPReportBean.STATUS_LOW:
                viewHolder.mTvHigh.setCompoundDrawables(drawableDown, null, null, null);
                break;
        }
        switch (bean.getLowPressureStatus()) {
            case BPReportBean.STATUS_HIGH:
                viewHolder.mTvLow.setCompoundDrawables(drawableUp, null, null, null);
                break;
            case BPReportBean.STATUS_NORMAL:
                viewHolder.mTvLow.setCompoundDrawables(null, null, null, null);
                break;
            case BPReportBean.STATUS_LOW:
                viewHolder.mTvLow.setCompoundDrawables(drawableDown, null, null, null);
                break;
        }
        switch (bean.getHeartRateStatus()) {
            case BPReportBean.STATUS_HIGH:
                viewHolder.mTvHeartRate.setCompoundDrawables(drawableUp, null, null, null);
                break;
            case BPReportBean.STATUS_NORMAL:
                viewHolder.mTvHeartRate.setCompoundDrawables(null, null, null, null);
                break;
            case BPReportBean.STATUS_LOW:
                viewHolder.mTvHeartRate.setCompoundDrawables(drawableDown, null, null, null);
                break;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_bp_report_item_time)
        TextView mTvTime;
        @Bind(R.id.tv_bp_report_item_high)
        TextView mTvHigh;
        @Bind(R.id.tv_bp_report_item_low)
        TextView mTvLow;
        @Bind(R.id.tv_bp_report_item_heart_rate)
        TextView mTvHeartRate;
        @Bind(R.id.tv_bp_report_item_high_range)
        TextView mTvHighRange;
        @Bind(R.id.tv_bp_report_item_low_range)
        TextView mTvLowRange;
        @Bind(R.id.tv_bp_report_item_heart_rate_range)
        TextView mTvHeartRateRange;
        
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
