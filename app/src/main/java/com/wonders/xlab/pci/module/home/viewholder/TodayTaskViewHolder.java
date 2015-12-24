package com.wonders.xlab.pci.module.home.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.wonders.xlab.common.recyclerview.adapter.MultiViewHolder;
import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.home.rxbus.BPClickBus;
import com.wonders.xlab.pci.module.home.rxbus.BSClickBus;
import com.wonders.xlab.pci.module.home.rxbus.MedicineClickBus;
import com.wonders.xlab.pci.module.home.rxbus.SymptomClickBus;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

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
    @Bind(R.id.tv_item_home_today_bp)
    TextView mTvBP;
    @Bind(R.id.tv_item_home_today_bs)
    TextView mTvBS;
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
        RxView.clicks(mTvMedicine)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        RxBus.getInstance().send(new MedicineClickBus());
                    }
                });
        RxView.clicks(mTvBP)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        RxBus.getInstance().send(new BPClickBus());
                    }
                });
        RxView.clicks(mTvBS)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        RxBus.getInstance().send(new BSClickBus());
                    }
                });
        RxView.clicks(mTvSymptom)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        RxBus.getInstance().send(new SymptomClickBus());
                    }
                });
        Glide.with(getContextWeakReference().get())
                .load(data.getPortrait())
                .transform(new GlideCircleTransform(getContextWeakReference().get()))
                .placeholder(R.drawable.user_avatar_default)
                .centerCrop()
                .crossFade()
                .into(mIvPortrait);

    }
}
