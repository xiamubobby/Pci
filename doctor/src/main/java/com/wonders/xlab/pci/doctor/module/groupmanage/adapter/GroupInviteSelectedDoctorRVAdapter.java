package com.wonders.xlab.pci.doctor.module.groupmanage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.pci.doctor.util.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.groupmanage.adapter.bean.GroupDoctorBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/4/7.
 */
public class GroupInviteSelectedDoctorRVAdapter extends SimpleRVAdapter<GroupDoctorBean> {

    public void unselectedDoctor(GroupDoctorBean bean) {
        int i = getBeanList().indexOf(bean);
        getBeanList().remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_manage_item_simple_icon_item, parent, false);
        return new ServiceIconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ServiceIconViewHolder viewHolder = (ServiceIconViewHolder) holder;
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), viewHolder.mIvServiceIcon, getBean(position).doctorAvatarUrl.get(), ImageViewManager.PLACE_HOLDER_EMPTY);
    }

    class ServiceIconViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_group_manage_item_simple_icon_item)
        ImageView mIvServiceIcon;

        public ServiceIconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}