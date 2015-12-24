package com.wonders.xlab.pci.module.record.monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.mvn.entity.record.monitor.SymptomEntity;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/22.
 */
public class SymptomRVAdapter extends SimpleRVAdapter<SymptomEntity.RetValuesEntity.UserSymptomAdvicesEntity> {

    public SymptomRVAdapter(WeakReference<Context> contextWeakReference) {
        super(contextWeakReference);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdviceViewHolder(mInflater.inflate(R.layout.item_symptom_advice, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        AdviceViewHolder viewHolder = (AdviceViewHolder) holder;
        viewHolder.mTvSymptomAdviceTime.setText(DateUtil.format(getBean(position).getRecordTime(), "HH:mm"));
        viewHolder.mTvSymptomAdviceContent.setText(getBean(position).getContent());
        Glide.with(getContextWeakReference().get())
                .load(getBean(position).getPortrait())
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .crossFade()
                .centerCrop()
                .into(viewHolder.mIvSymptomAdvicePortrait);
    }

    class AdviceViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_symptom_advice_time)
        TextView mTvSymptomAdviceTime;
        @Bind(R.id.iv_symptom_advice_portrait)
        ImageView mIvSymptomAdvicePortrait;
        @Bind(R.id.tv_symptom_advice_content)
        TextView mTvSymptomAdviceContent;

        public AdviceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
