package com.wonders.xlab.patient.module.dailyreport.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BSReportRealmBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/3/20.
 */
public class BSReportAdapter extends SimpleRVAdapter<BSReportRealmBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bs_report_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        BSReportRealmBean bean = getBean(position);
        viewHolder.mTvBs.setText(String.format("%s：%smmol/L", bean.getMeasurePeriod(), bean.getBloodSugar()));
        viewHolder.mTvTime.setText(String.format("记录时间：%s", DateUtil.format(bean.getRecordTimeInMill(), "HH:mm")));
        viewHolder.mTvRange.setText(bean.getContent());

        Drawable drawableUp = viewHolder.itemView.getContext().getResources().getDrawable(R.drawable.pic_arrow_up);
        if (drawableUp != null) {
            drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        }
        Drawable drawableDown = viewHolder.itemView.getContext().getResources().getDrawable(R.drawable.pic_arrow_down);
        if (drawableDown != null) {
            drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
        }

        switch (bean.getBloodSugarStatus()) {
            case BSReportRealmBean.STATUS_HIGH:
                viewHolder.mTvBs.setCompoundDrawables(drawableUp, null, null, null);
                break;
            case BSReportRealmBean.STATUS_NORMAL:
                viewHolder.mTvBs.setCompoundDrawables(null, null, null, null);
                break;
            case BSReportRealmBean.STATUS_LOW:
                viewHolder.mTvBs.setCompoundDrawables(drawableDown, null, null, null);
                break;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_bs_report_item_time)
        TextView mTvTime;
        @Bind(R.id.tv_bs_report_item)
        TextView mTvBs;
        @Bind(R.id.tv_bs_report_item_range)
        TextView mTvRange;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
