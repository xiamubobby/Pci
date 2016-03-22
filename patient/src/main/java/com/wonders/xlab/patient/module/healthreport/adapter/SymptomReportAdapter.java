package com.wonders.xlab.patient.module.healthreport.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.SymptomReportLabelBean;
import com.zhy.view.flowlayout.FlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/3/21.
 */
public class SymptomReportAdapter extends SimpleRVAdapter<SymptomReportBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_report_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        SymptomReportBean bean = getBean(position);

        Resources resources = viewHolder.itemView.getContext().getResources();

        viewHolder.mTvAdvice.setText(bean.getAdvice());
        viewHolder.mTvTime.setText(String.format("记录时间：%s", DateUtil.format(bean.getRecordTimeInMill(), "HH:mm")));
        if (bean.isHasConfirmed()) {
            viewHolder.mTvStatus.setText("已确认");
            viewHolder.mTvStatus.setTextColor(resources.getColor(R.color.appYellow));
            viewHolder.mTvStatus.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_symptom_tv_confirm));
        } else {
            viewHolder.mTvStatus.setText("未确认");
            viewHolder.mTvStatus.setTextColor(resources.getColor(R.color.colorAccent));
            viewHolder.mTvStatus.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_symptom_tv_not_confirm));
        }

        viewHolder.mFlSymptoms.removeAllViews();

        LayoutInflater layoutInflater = LayoutInflater.from(viewHolder.itemView.getContext());
        for (SymptomReportLabelBean symptom : bean.getSymptomList()) {
            final View itemView = layoutInflater.inflate(R.layout.symptom_report_item_symptom_label, (ViewGroup) viewHolder.itemView, false);
            TextView tvSymptom = (TextView) itemView.findViewById(R.id.tv_symptom_report_item_symptom_label);
            if (bean.isHasConfirmed()) {
                tvSymptom.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_symptom_label_confirmed));
            } else {
                tvSymptom.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_symptom_label_not_confirm));
            }
            tvSymptom.setText(symptom.getSymptomStr());
            viewHolder.mFlSymptoms.addView(itemView);
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_symptom_report_item_status)
        TextView mTvStatus;
        @Bind(R.id.tv_symptom_report_item_time)
        TextView mTvTime;
        @Bind(R.id.fl_symptom_report_item_symptoms)
        FlowLayout mFlSymptoms;
        @Bind(R.id.tv_symptom_report_item_advice)
        TextView mTvAdvice;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
