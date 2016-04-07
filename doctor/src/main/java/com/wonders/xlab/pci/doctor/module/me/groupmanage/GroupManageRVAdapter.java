package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.GroupManageItemBinding;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.bean.GroupManageBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.bean.ServiceIconBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/4/7.
 */
public class GroupManageRVAdapter extends SimpleRVAdapter<GroupManageBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_manage_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        GroupManageBean bean = getBean(position);
        viewHolder.binding.setBean(bean);
        //TODO 头像组件还有待开发
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), viewHolder.mIvPortrait, bean.getAvatarList().get(0), ImageViewManager.PLACE_HOLDER_EMPTY);

        viewHolder.mRecyclerViewServices.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        viewHolder.mRecyclerViewServices.setItemAnimator(new DefaultItemAnimator());
        ServiceIconRVAdapter adapter = new ServiceIconRVAdapter();
        adapter.setDatas(bean.getServiceIconList());
        viewHolder.mRecyclerViewServices.setAdapter(adapter);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_group_manage_item_portrait)
        ImageView mIvPortrait;
        @Bind(R.id.recycler_view_doctor_all_item_services)
        RecyclerView mRecyclerViewServices;

        GroupManageItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = GroupManageItemBinding.bind(itemView);
        }
    }

    class ServiceIconRVAdapter extends SimpleRVAdapter<ServiceIconBean> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_manage_item_service_icon_item, parent, false);
            return new ServiceIconViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            ServiceIconViewHolder viewHolder = (ServiceIconViewHolder) holder;
            ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(),viewHolder.mIvServiceIcon,getBean(position).getIconUrl(),ImageViewManager.PLACE_HOLDER_EMPTY);
        }

        class ServiceIconViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_group_manage_item_service_icon_item)
            ImageView mIvServiceIcon;

            public ServiceIconViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }
}
