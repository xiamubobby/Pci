package com.wonders.xlab.pci.module.home.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.databinding.ItemHomeHistoryTaskBinding;
import com.wonders.xlab.pci.module.home.bean.HistoryTaskBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/14.
 */
public class HistoryTaskViewHolder extends MultiViewHolder<HistoryTaskBean> {
    ItemHomeHistoryTaskBinding binding;

    @Bind(R.id.iv_item_home_yesterday_portrait)
    ImageView mIvPortrait;

    public HistoryTaskViewHolder(View itemView) {
        super(itemView);
        binding = ItemHomeHistoryTaskBinding.bind(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(HistoryTaskBean data) {
        binding.setHistory(data);
        Glide.with(getContextWeakReference().get())
                .load(data.getPortrait())
                .centerCrop()
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .placeholder(R.drawable.user_avatar_default)
                .crossFade()
                .into(mIvPortrait);
    }
}
