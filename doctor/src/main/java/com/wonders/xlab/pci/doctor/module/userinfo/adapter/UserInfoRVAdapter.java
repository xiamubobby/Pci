package com.wonders.xlab.pci.doctor.module.userinfo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.UserInfoItemBinding;
import com.wonders.xlab.pci.doctor.module.userinfo.bean.UserInfoBean;

import butterknife.ButterKnife;

/**
 * Created by hua on 16/2/23.
 */
public class UserInfoRVAdapter extends SimpleRVAdapter<UserInfoBean> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_info_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        UserInfoViewHolder viewHolder = (UserInfoViewHolder) holder;
        viewHolder.mBinding.setUserInfo(getBean(position));
    }

    class UserInfoViewHolder extends RecyclerView.ViewHolder{

        UserInfoItemBinding mBinding;
        public UserInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mBinding = UserInfoItemBinding.bind(itemView);
        }
    }
}
