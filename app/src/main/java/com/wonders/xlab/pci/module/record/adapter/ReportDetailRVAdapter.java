package com.wonders.xlab.pci.module.record.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTvTitle.setText(getBean(position).getTitle());

        //setup inner recyclerview
        viewHolder.mRv.setLayoutManager(new GridLayoutManager(mContext.get(), 3, LinearLayoutManager.VERTICAL, false));
        InnerImageRVAdapter imageRVAdapter = new InnerImageRVAdapter();
        imageRVAdapter.setDatas(getBean(position).getPicUrlList());
        viewHolder.mRv.setAdapter(imageRVAdapter);

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_report_detail_title)
        TextView mTvTitle;
        @Bind(R.id.rv_item_report_detail)
        RecyclerView mRv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
