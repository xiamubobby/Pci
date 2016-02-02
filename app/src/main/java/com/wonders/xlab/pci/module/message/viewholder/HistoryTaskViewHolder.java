package com.wonders.xlab.pci.module.message.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.message.bean.HistoryTaskBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 15/12/14.
 */
public class HistoryTaskViewHolder extends MultiViewHolder<HistoryTaskBean> {

    @Bind(R.id.iv_item_home_yesterday_portrait)
    ImageView mIvPortrait;
    @Bind(R.id.tv_item_home_history_title)
    TextView mTvItemHomeHistoryTitle;
    @Bind(R.id.tv_item_home_history_content)
    TextView mTvItemHomeHistoryContent;
    @Bind(R.id.tv_item_home_history_time)
    TextView mTvItemHomeHistoryTime;

    public HistoryTaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(HistoryTaskBean data) {
        mTvItemHomeHistoryTitle.setText(data.getTitle());
        mTvItemHomeHistoryContent.setText(data.getContent());
        mTvItemHomeHistoryTime.setText(DateUtil.format(data.getUpdateTime(),DateUtil.DEFAULT_FORMAT_FULL));
        Glide.with(getContextWeakReference().get())
                .load(data.getPortrait())
                .centerCrop()
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .placeholder(R.drawable.user_avatar_default)
                .crossFade()
                .into(mIvPortrait);
    }
}
