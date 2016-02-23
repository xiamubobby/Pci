package com.wonders.xlab.pci.doctor.module.bs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.BsItemBinding;
import com.wonders.xlab.pci.doctor.module.bs.bean.BSBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/2/23.
 */
public class BSRVAdapter extends SimpleRVAdapter<BSBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>{
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bs_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mBinding.setBs(getBean(position));
    }

    @Override
    public long getHeaderId(int position) {
        return getBean(position).getHeaderId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View titleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bs_item_header, parent, false);
        return new TitleViewHolder(titleView);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TitleViewHolder viewHolder = (TitleViewHolder) holder;
        String time = "";
        for (int i = 0; i < getItemCount(); i++) {
            if (getBean(i).getHeaderId() == getHeaderId(position)) {
                time = getBean(i).getTime();
                break;
            }
        }
        viewHolder.bpTitle.setText(time.substring(0,7));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        BsItemBinding mBinding;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mBinding = BsItemBinding.bind(itemView);
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_bs_item_header)
        TextView bpTitle;

        public TitleViewHolder(View titleView) {
            super(titleView);
            ButterKnife.bind(this, titleView);
        }
    }
}
