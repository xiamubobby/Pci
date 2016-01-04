package com.wonders.xlab.pci.module.home.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.databinding.ItemHomeTodayTaskBinding;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.home.otto.BPClickBus;
import com.wonders.xlab.pci.module.home.otto.BSClickBus;
import com.wonders.xlab.pci.module.home.otto.MedicineClickBus;
import com.wonders.xlab.pci.module.home.otto.SymptomClickBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/14.
 */
public class TodayTaskViewHolder extends MultiViewHolder<TodayTaskBean> {
    ItemHomeTodayTaskBinding binding;

    @Bind(R.id.iv_item_home_today_portrait)
    ImageView mIvPortrait;

    public TodayTaskViewHolder(View itemView) {
        super(itemView);
        binding = ItemHomeTodayTaskBinding.bind(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(TodayTaskBean data) {
        binding.setTask(data);
        binding.setHandler(this);

        Glide.with(getContextWeakReference().get())
                .load(data.getPortrait())
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .placeholder(R.drawable.user_avatar_default)
                .centerCrop()
                .crossFade()
                .into(mIvPortrait);

    }

    public void onClickMedicine(View view) {
        OttoManager.post(new MedicineClickBus());
    }

    public void onClickBP(View view) {
        OttoManager.post(new BPClickBus());
    }

    public void onClickBS(View view) {
        OttoManager.post(new BSClickBus());
    }

    public void onClickSymptom(View view) {
        OttoManager.post(new SymptomClickBus());
    }
}
