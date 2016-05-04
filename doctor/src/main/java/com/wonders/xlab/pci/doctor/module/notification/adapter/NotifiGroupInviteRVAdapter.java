package com.wonders.xlab.pci.doctor.module.notification.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.NotifiGroupInviteItemBinding;
import com.wonders.xlab.pci.doctor.module.notification.adapter.bean.NotifiGroupInviteBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.avatarassemble.library.MultiAvatarView;

/**
 * Created by hua on 16/4/14.
 */
public class NotifiGroupInviteRVAdapter extends SimpleRVAdapter<NotifiGroupInviteBean> {

    private OnOperationClickListener mOnOperationClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notifi_group_invite_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NotifiGroupInviteBean bean = getBean(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.binding.setBean(bean);
        viewHolder.mBtnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnOperationClickListener) {
                    mOnOperationClickListener.onAgreeClick(holder.getAdapterPosition());
                }
            }
        });
        viewHolder.mBtnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnOperationClickListener) {
                    mOnOperationClickListener.onRefuseClick(holder.getAdapterPosition());
                }
            }
        });
        viewHolder.mIvPortrait.setAvatarUrls(bean.getAvatarUrls());
    }

    public void setOnOperationClickListener(OnOperationClickListener onOperationClickListener) {
        mOnOperationClickListener = onOperationClickListener;
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_notifi_group_invite_item_portrait)
        MultiAvatarView mIvPortrait;
        @Bind(R.id.btn_notifi_group_invite_item_refuse)
        Button mBtnRefuse;
        @Bind(R.id.btn_notifi_group_invite_item_agree)
        Button mBtnAgree;

        NotifiGroupInviteItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = NotifiGroupInviteItemBinding.bind(itemView);
        }
    }

    public interface OnOperationClickListener {
        void onAgreeClick(int position);

        void onRefuseClick(int position);
    }
}
