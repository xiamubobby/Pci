package com.wonders.xlab.patient.module.mydoctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.DoctorMyItemBinding;
import com.wonders.xlab.patient.module.chatroom.otto.ChatRoomRecordInsertOtto;
import com.wonders.xlab.patient.module.otto.MainBottomUnreadNotifyCountOtto;
import com.wonders.xlab.patient.util.UnReadMessageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;
import im.hua.avatarassemble.library.MultiAvatarView;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/3/14.
 */
public class MyDoctorRVAdapter extends SimpleRVAdapter<MyDoctorItemBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    /**
     * 重新排序，并且将最新的数据更新
     *
     * @param otto
     */
    @Subscribe
    public void receiveNotifyForUpdate(ChatRoomRecordInsertOtto otto) {
        List<MyDoctorItemBean> beanList = getBeanList();
        if (beanList.size() == 1) {
            beanList.get(0).setLatestChatMessage(otto.getTxtContent());
            beanList.get(0).setTimeStr(DateUtil.format(otto.getMessageTime(), "HH:mm"));
        } else {
            MyDoctorItemBean bean;

            for (int i = 0; i < beanList.size(); i++) {
                //TODO 到底能不能用groupId来区分一个组？？？？？
                if (beanList.get(i).getImGroupId().equals(otto.getImGroupId())) {
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

    /**
     * 更新数字图标
     *
     * @param otto
     */
    @Subscribe
    public void changeDoctorNotifyCounts(MainBottomUnreadNotifyCountOtto otto) {
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
        BGABadgeViewHelper badgeViewHelper = viewHolder.mIvPortraitContainer.getBadgeViewHelper();
        badgeViewHelper.setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        if (counts > 0 && counts < 100) {
            viewHolder.mIvPortraitContainer.showTextBadge(String.valueOf(counts));
        } else if (counts >= 100) {
            viewHolder.mIvPortraitContainer.showTextBadge("99+");
        } else {
            viewHolder.mIvPortraitContainer.hiddenBadge();
        }
        viewHolder.mIvPortrait.setAvatarUrls(dataItem.getPortraitUrl());

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
        @Bind(R.id.iv_doctor_my_item_portrait)
        MultiAvatarView mIvPortrait;
        @Bind(R.id.iv_doctor_my_item_portrait_container)
        BGABadgeFrameLayout mIvPortraitContainer;

        DoctorMyItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = DoctorMyItemBinding.bind(itemView);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        OttoManager.unregister(this);
    }
}
