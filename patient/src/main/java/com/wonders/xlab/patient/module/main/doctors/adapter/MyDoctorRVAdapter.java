package com.wonders.xlab.patient.module.main.doctors.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorMyItemBinding;
import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;
import com.wonders.xlab.patient.module.main.doctors.otto.ChatNotifyCountOtto;
import com.wonders.xlab.patient.receiver.otto.EMChatMessageOtto;
import com.wonders.xlab.patient.util.UnReadMessageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/3/14.
 */
public class MyDoctorRVAdapter extends SimpleRVAdapter<MyDoctorItemBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    @Subscribe
    public void receiveNotifyForUpdate(EMChatMessageOtto otto) {
        List<MyDoctorItemBean> beanList = getBeanList();
        if (beanList.size() == 1) {
            beanList.get(0).setLatestChatMessage(otto.getTxtContent());
            beanList.get(0).setTimeStr(DateUtil.format(otto.getMessageTime(), "HH:mm"));
        } else {
            MyDoctorItemBean bean;

            for (int i = 0; i < beanList.size(); i++) {
                if (beanList.get(i).getGroupId().equals(otto.getGroupId())) {
                    if (i > 0) {
                        bean = new MyDoctorItemBean();
                        bean.setPortraitUrl(beanList.get(i).getPortraitUrl());
                        bean.setType(beanList.get(i).getType());

                        remove(i);
                        bean.setGroupId(otto.getGroupId());
                        bean.setImGroupId(otto.getImGroupId());
                        bean.setDoctorGroupName(otto.getGroupName());
                        bean.setLatestChatMessage(otto.getTxtContent());
                        insertToFist(bean);
                    } else {
                        bean = beanList.get(i);
                        bean.setLatestChatMessage(otto.getTxtContent());
                        bean.setTimeStr(DateUtil.format(otto.getMessageTime(), "HH:mm"));
                        notifyItemChanged(i);
                    }
                    return;
                }
            }
        }
    }

    @Subscribe
    public void changeDoctorNotifyCounts(ChatNotifyCountOtto otto) {
        for (int i = 0; i < getBeanList().size(); i++) {
            if (otto.getImGroupId().equals(getBeanList().get(i).getImGroupId())) {
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        OttoManager.register(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_my_item, parent, false);
        return new ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyDoctorItemBean dataItem = getBean(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setDoctorBean(dataItem);

        int counts = UnReadMessageUtil.getUnreadMessageCounts(dataItem.getImGroupId());
        BGABadgeViewHelper badgeViewHelper = viewHolder.mIvPortrait.getBadgeViewHelper();
        badgeViewHelper.setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        if (counts > 0 && counts < 100) {
            viewHolder.mIvPortrait.showTextBadge(String.valueOf(counts));
        } else if (counts >= 100) {
            viewHolder.mIvPortrait.showTextBadge("99+");
        } else {
            viewHolder.mIvPortrait.hiddenBadge();
        }

        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), viewHolder.mIvPortrait, dataItem.getPortraitUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    @Override
    public long getHeaderId(int position) {
        return getBean(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_my_item_header, parent, false);
        return new ItemHeaderViewHolder(headerView);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHeaderViewHolder viewHolder = (ItemHeaderViewHolder) holder;
        if (MyDoctorItemBean.TYPE_IN_SERVICE == getHeaderId(position)) {
            viewHolder.mTvDoctorMyItemHeader.setText("服务中");
        } else if (MyDoctorItemBean.TYPE_OUT_OF_SERVICE == getHeaderId(position)) {
            viewHolder.mTvDoctorMyItemHeader.setText("历史记录");
        }
    }

    class ItemHeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_doctor_my_item_header)
        TextView mTvDoctorMyItemHeader;

        public ItemHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        BGABadgeImageView mIvPortrait;

        DoctorMyItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mIvPortrait = (BGABadgeImageView) itemView.findViewById(R.id.iv_doctor_my_item_portrait);
            binding = DoctorMyItemBinding.bind(itemView);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        OttoManager.unregister(this);
    }
}
