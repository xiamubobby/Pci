package com.wonders.xlab.pci.module.home.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.common.adapter.recyclerview.MultiViewHolder;
import com.wonders.xlab.common.utils.DateUtil;
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
        mTvTime.setText(DateUtil.format(data.getUpdateTime(),"yyyy-MM-dd hh:mm"));
        mTvName.setText(data.getName());
//        Glide.with()
    }
}
