package com.wonders.xlab.pci.module.message.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.message.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.message.otto.BPClickBus;
import com.wonders.xlab.pci.module.message.otto.BSClickBus;
import com.wonders.xlab.pci.module.message.otto.MedicineClickBus;
import com.wonders.xlab.pci.module.message.otto.SymptomClickBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hua on 15/12/14.
 */
public class TodayTaskViewHolder extends MultiViewHolder<TodayTaskBean> {

    @Bind(R.id.iv_item_home_today_portrait)
    ImageView mIvPortrait;
    @Bind(R.id.tv_item_home_today_name)
    TextView mTvItemHomeTodayName;
    @Bind(R.id.tv_item_home_today_title)
    TextView mTvItemHomeTodayTitle;
    @Bind(R.id.tv_item_home_today_time)
    TextView mTvItemHomeTodayTime;

    public TodayTaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(TodayTaskBean data) {
        mTvItemHomeTodayName.setText(data.getName());
        mTvItemHomeTodayTitle.setText(data.getTitle());
        mTvItemHomeTodayTime.setText(DateUtil.format(data.getUpdateTime(),DateUtil.DEFAULT_FORMAT_FULL));
        Glide.with(getContextWeakReference().get())
                .load(data.getPortrait())
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .placeholder(R.drawable.user_avatar_default)
                .centerCrop()
                .crossFade()
                .into(mIvPortrait);

    }

    @OnClick(R.id.tv_item_home_today_medicine)
    public void onClickMedicine(View view) {
        OttoManager.post(new MedicineClickBus());
    }

    @OnClick(R.id.tv_item_home_today_bp)
    public void onClickBP(View view) {
        OttoManager.post(new BPClickBus());
    }

    @OnClick(R.id.tv_item_home_today_bs)
    public void onClickBS(View view) {
        OttoManager.post(new BSClickBus());
    }

    @OnClick(R.id.tv_item_home_today_symptom)
    public void onClickSymptom(View view) {
        OttoManager.post(new SymptomClickBus());
    }
}
