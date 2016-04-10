package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.GroupInviteItemBinding;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/4/8.
 * 可多选的医生列表适配器
 */
public class GroupDoctorMultiChoiceRVAdapter extends SimpleRVAdapter<GroupDoctorBean> {

    private OnItemClickListener mOnItemClickListener;

    public GroupDoctorMultiChoiceRVAdapter() {
        super.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GroupDoctorBean tmp = getBean(position);
                tmp.isSelected.set(!tmp.isSelected.get());
                notifyItemChanged(position);

                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_invite_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        GroupDoctorBean bean = getBean(position);
        viewHolder.binding.setBean(bean);
        ImageViewManager.setImageViewWithUrl(viewHolder.itemView.getContext(),viewHolder.mIvAvatar,bean.doctorAvatarUrl.get(),ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_group_invite_item_avatar)
        ImageView mIvAvatar;

        GroupInviteItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            binding = GroupInviteItemBinding.bind(itemView);
        }
    }
}
