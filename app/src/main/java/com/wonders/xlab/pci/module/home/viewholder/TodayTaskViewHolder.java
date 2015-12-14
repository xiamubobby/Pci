package com.wonders.xlab.pci.module.home.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.adapter.recyclerview.MultiViewHolder;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/14.
 */
public class TodayTaskViewHolder extends MultiViewHolder<TodayTaskBean> {
    @Bind(R.id.tv_item_home_today_name)
    TextView mTvName;
    @Bind(R.id.iv_item_home_today_portrait)
    ImageView mIvPortrait;
    @Bind(R.id.tv_item_home_today_title)
    TextView mTvTitle;
    @Bind(R.id.tv_item_home_today_time)
    TextView mTvTime;
    @Bind(R.id.tv_item_home_today_medicine)
    TextView mTvMedicine;
    @Bind(R.id.tv_item_home_today_measure)
    TextView mTvMeasure;
    @Bind(R.id.tv_item_home_today_symptom)
    TextView mTvSymptom;

    public TodayTaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(TodayTaskBean data) {
        mTvTitle.setText(data.getTitle());
        mTvTime.setText(DateUtil.format(data.getUpdateTime(), "yyyy-MM-dd hh:mm"));
        mTvName.setText(data.getName());
        Glide.with(getContextWeakReference().get())
                .load(data.getPortrait())
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .placeholder(R.drawable.user_avatar_default)
                .centerCrop()
                .crossFade()
                .into(mIvPortrait);

    }
}
