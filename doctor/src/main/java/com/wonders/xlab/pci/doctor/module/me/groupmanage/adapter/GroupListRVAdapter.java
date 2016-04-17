package com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wonders.xlab.pci.doctor.util.ImageViewManager;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.GroupManageItemBinding;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupListBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/4/7.
 */
public class GroupListRVAdapter extends SimpleRVAdapter<GroupListBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_manage_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        GroupListBean bean = getBean(position);
        viewHolder.binding.setBean(bean);
        //TODO 头像组件还有待开发
        String avatarUrl = "";
        if (bean.getAvatarList() != null && bean.getAvatarList().size() > 0) {
            avatarUrl = bean.getAvatarList().get(0);
        }
        ImageViewManager.setImageViewWithUrl(holder.itemView.getContext(), viewHolder.mIvPortrait, avatarUrl, ImageViewManager.PLACE_HOLDER_EMPTY);

        viewHolder.mRecyclerViewServices.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        viewHolder.mRecyclerViewServices.setItemAnimator(new DefaultItemAnimator());
        GroupServiceIconRVAdapter adapter = new GroupServiceIconRVAdapter();
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

}