package com.wonders.xlab.pci.module.record.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wonders.xlab.common.adapter.recyclerview.SimpleRVAdapter;
import com.wonders.xlab.common.utils.GlideCircleTransform;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.record.bean.ReportDetailBean;
import com.wonders.xlab.pci.module.record.bean.ReportDetailImageBean;
import com.zhy.view.flowlayout.FlowLayout;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
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
                                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) (view.getLayoutParams() == null ? new ViewGroup.MarginLayoutParams(width / 2, ViewGroup.LayoutParams.WRAP_CONTENT) : view.getLayoutParams());
                                view.setLayoutParams(layoutParams);
                                return new InnerImageBean(view, reportDetailImageBean);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<InnerImageBean>() {
                            @Override
                            public void call(InnerImageBean imageBean) {
                                View view = imageBean.getView();
                                ImageView mTvImg = (ImageView) view.findViewById(R.id.tv_item_report_detail_image_img);
                                TextView mTvName = (TextView) view.findViewById(R.id.tv_item_report_detail_image_name);
                                mTvName.setText(imageBean.getImageBean().getName());
                                Glide.with(mContext.get())
                                        .load(imageBean.getImageBean().getPortraitUrl())
                                        .centerCrop()
                                        .transform(new GlideCircleTransform(mContext.get()))
                                        .placeholder(R.drawable.user_avatar_default)
                                        .crossFade()
                                        .into(mTvImg);

                                viewHolder.mFlItemReportDetail.addView(view);
                            }
                        });
            }
        });

        //setup inner recyclerview
        /*viewHolder.mRv.setLayoutManager(new GridLayoutManager(mContext.get(), 3, LinearLayoutManager.VERTICAL, false));
        InnerImageRVAdapter imageRVAdapter = new InnerImageRVAdapter();
        imageRVAdapter.setDatas(getBean(position).getPicUrlList());
        viewHolder.mRv.setAdapter(imageRVAdapter);*/
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

        public void setView(View view) {
            this.mView = view;
        }

        public ReportDetailImageBean getImageBean() {
            return mImageBean;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_report_detail_title)
        TextView mTvTitle;
//        @Bind(R.id.rv_item_report_detail)
//        RecyclerView mRv;
        @Bind(R.id.fl_item_report_detail)
        FlowLayout mFlItemReportDetail;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class InnerImageRVAdapter extends SimpleRVAdapter<ReportDetailImageBean> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemImageViewHolder(mInflater.inflate(R.layout.item_report_detail_image, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            ItemImageViewHolder viewHolder = (ItemImageViewHolder) holder;
            viewHolder.mTvName.setText(getBean(position).getName());
            Glide.with(mContext.get())
                    .load(getBean(position).getPortraitUrl())
                    .centerCrop()
                    .transform(new GlideCircleTransform(mContext.get()))
                    .placeholder(R.drawable.user_avatar_default)
                    .crossFade()
                    .into(viewHolder.mTvImg);
        }

        class ItemImageViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_item_report_detail_image_img)
            ImageView mTvImg;
            @Bind(R.id.tv_item_report_detail_image_name)
            TextView mTvName;

            public ItemImageViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }


}
