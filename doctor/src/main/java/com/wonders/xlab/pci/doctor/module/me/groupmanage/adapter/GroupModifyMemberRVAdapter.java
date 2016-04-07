package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyMemberBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/4/7.
 */
public class GroupModifyMemberRVAdapter extends SimpleRVAdapter<GroupModifyMemberBean> {

    @Override
    public void appendDatas(List<GroupModifyMemberBean> mBeanList) {
        if (mBeanList == null || mBeanList.size() <= 0) {
            return;
        }

        List<GroupModifyMemberBean> beanList = getBeanList();

        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).getType() != GroupModifyMemberBean.TYPE_MEMBER) {
                beanList.remove(i);
            }
        }

        beanList.addAll(mBeanList);

        int size = beanList.size();
        if (size < 9) {
            GroupModifyMemberBean addBean = new GroupModifyMemberBean();
            addBean.setType(GroupModifyMemberBean.TYPE_ADD);
            appendToLast(addBean);
        }
        if (size > 1) {
            GroupModifyMemberBean minusBean = new GroupModifyMemberBean();
            minusBean.setType(GroupModifyMemberBean.TYPE_MINUS);
            appendToLast(minusBean);
        }

        notifyDataSetChanged();
    }

    @Override
    public void setDatas(List<GroupModifyMemberBean> mBeanList) {
        super.setDatas(mBeanList);
        int size = getBeanList().size();
        if (size < 9) {
            GroupModifyMemberBean addBean = new GroupModifyMemberBean();
            addBean.setType(GroupModifyMemberBean.TYPE_ADD);
            appendToLast(addBean);
        }
        if (size > 1) {
            GroupModifyMemberBean minusBean = new GroupModifyMemberBean();
            minusBean.setType(GroupModifyMemberBean.TYPE_MINUS);
            appendToLast(minusBean);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_modify_member_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        GroupModifyMemberBean bean = getBean(position);
        switch (bean.getType()) {
            case GroupModifyMemberBean.TYPE_MEMBER:
                viewHolder.mTvName.setText(bean.getMemberName());
                ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(), viewHolder.mIvAvatar, bean.getAvatarUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
                break;
            case GroupModifyMemberBean.TYPE_ADD:
                viewHolder.mTvName.setVisibility(View.GONE);
                viewHolder.mIvAvatar.setImageResource(R.drawable.ic_group_member_add);
                break;
            case GroupModifyMemberBean.TYPE_MINUS:
                viewHolder.mTvName.setVisibility(View.GONE);
                viewHolder.mIvAvatar.setImageResource(R.drawable.ic_group_member_minus);
                break;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_group_modify_member_avatar)
        ImageView mIvAvatar;
        @Bind(R.id.tv_group_modify_member_name)
        TextView mTvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
