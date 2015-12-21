package com.wonders.xlab.pci.module.record.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.bean.ReportDetailBean;
import com.wonders.xlab.pci.module.record.bean.ReportDetailImageBean;
import com.zhy.view.flowlayout.FlowLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPagerActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hua on 15/12/14.
 */
public class ReportDetailRVAdapter extends SimpleRVAdapter<ReportDetailBean> {

    private WeakReference<Context> mContext;
    private LayoutInflater mInflater;

    public ReportDetailRVAdapter(WeakReference<Context> contextWeakReference) {
        super(contextWeakReference);
        mContext = contextWeakReference;
        mInflater = LayoutInflater.from(mContext.get());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_report_detail, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTvTitle.setText(getBean(position).getTitle());
        viewHolder.mFlItemReportDetail.removeAllViews();
        viewHolder.mFlItemReportDetail.post(new Runnable() {
            @Override
            public void run() {
                Observable.from(getBean(position).getPicUrlList())
                        .map(new Func1<ReportDetailImageBean, InnerImageBean>() {
                            @Override
                            public InnerImageBean call(ReportDetailImageBean reportDetailImageBean) {
                                int width = viewHolder.mFlItemReportDetail.getMeasuredWidth();
                                final View view = mInflater.inflate(R.layout.item_report_detail_image, null, false);
                                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)
                                        (view.getLayoutParams() == null ? new ViewGroup.MarginLayoutParams(width / 2, ViewGroup.LayoutParams.WRAP_CONTENT) : view.getLayoutParams());
                                view.setLayoutParams(layoutParams);
                                return new InnerImageBean(view, reportDetailImageBean);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<InnerImageBean>() {
                            @Override
                            public void call(final InnerImageBean imageBean) {
                                View view = imageBean.getView();
                                ImageView mTvImg = (ImageView) view.findViewById(R.id.tv_item_report_detail_image_img);
                                TextView mTvName = (TextView) view.findViewById(R.id.tv_item_report_detail_image_name);
                                mTvName.setText(imageBean.getImageBean().getName());
                                Glide.with(mContext.get())
                                        .load(imageBean.getImageBean().getPortraitUrl())
                                        .centerCrop()
                                        .placeholder(R.drawable.user_avatar_default)
                                        .crossFade()
                                        .into(mTvImg);
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ArrayList<String> picUrls = new ArrayList<>();
                                        for (ReportDetailImageBean detailBean : getBean(position).getPicUrlList()) {
                                            picUrls.add(detailBean.getPortraitUrl());
                                        }
                                        Intent intent = new Intent(mContext.get(), PhotoPagerActivity.class);
                                        intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, imageBean.getImageBean().getPosition());
                                        intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, picUrls);
                                        mContext.get().startActivity(intent);

                                    }
                                });
                                viewHolder.mFlItemReportDetail.addView(view);
                            }
                        });
            }
        });

    }

    class InnerImageBean {
        private View mView;
        private ReportDetailImageBean mImageBean;

        public InnerImageBean(View view, ReportDetailImageBean imageBean) {
            mView = view;
            mImageBean = imageBean;
        }

        public View getView() {
            return mView;
        }

        public ReportDetailImageBean getImageBean() {
            return mImageBean;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_report_detail_title)
        TextView mTvTitle;
        @Bind(R.id.fl_item_report_detail)
        FlowLayout mFlItemReportDetail;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
