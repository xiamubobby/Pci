package com.wonders.xlab.pci.doctor.module.me.notification.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.NotifiGroupInviteItemBinding;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiGroupInviteBean;
import com.wonders.xlab.pci.doctor.util.ImageViewManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/4/14.
 */
public class NotifiGroupInviteRVAdapter extends SimpleRVAdapter<NotifiGroupInviteBean> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notifi_group_invite_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NotifiGroupInviteBean bean = getBean(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setBean(bean);
        ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(),viewHolder.mIvPortrait,bean.getOwnerAvatarUrl(),R.drawable.ic_default_avatar_doctor);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_group_manage_item_portrait)
        ImageView mIvPortrait;

        NotifiGroupInviteItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = NotifiGroupInviteItemBinding.bind(itemView);
        }
    }
}
