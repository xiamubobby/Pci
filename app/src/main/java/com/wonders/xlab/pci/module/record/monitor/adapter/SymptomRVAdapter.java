package com.wonders.xlab.pci.module.record.monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.databinding.ItemSymptomAdviceBinding;
import com.wonders.xlab.pci.module.base.mvn.entity.record.monitor.SymptomEntity;

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
        viewHolder.binding.setAdvice(getBean(position));
        Glide.with(getContextWeakReference().get())
                .load(getBean(position).getPortrait())
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .crossFade()
                .centerCrop()
                .into(viewHolder.mIvSymptomAdvicePortrait);
    }

    class AdviceViewHolder extends RecyclerView.ViewHolder {
        ItemSymptomAdviceBinding binding;

        @Bind(R.id.iv_symptom_advice_portrait)
        ImageView mIvSymptomAdvicePortrait;

        public AdviceViewHolder(View itemView) {
            super(itemView);
            binding = ItemSymptomAdviceBinding.bind(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
