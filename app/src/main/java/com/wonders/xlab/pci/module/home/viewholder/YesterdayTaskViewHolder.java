package com.wonders.xlab.pci.module.home.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.home.bean.YesterdayTaskBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/14.
 */
public class YesterdayTaskViewHolder extends MultiViewHolder<YesterdayTaskBean> {
    @Bind(R.id.tv_item_home_yesterday_name)
    TextView mTvName;
    @Bind(R.id.iv_item_home_yesterday_portrait)
    ImageView mIvPortrait;
    @Bind(R.id.tv_item_home_yesterday_title)
    TextView mTvTitle;
    @Bind(R.id.tv_item_home_yesterday_content)
    TextView mTvContent;
    @Bind(R.id.tv_item_home_yesterday_time)
    TextView mTvTime;

    public YesterdayTaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(YesterdayTaskBean data) {
        mTvTitle.setText(data.getTitle());
        mTvContent.setText(data.getContent());
        mTvTime.setText(DateUtil.format(data.getUpdateTime(), "yyyy-MM-dd HH:mm"));
        mTvName.setText(data.getName());
        Glide.with(getContextWeakReference().get())
                .load(data.getPortrait())
                .centerCrop()
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .placeholder(R.drawable.user_avatar_default)
                .crossFade()
                .into(mIvPortrait);
    }
}
