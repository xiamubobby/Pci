package com.wonders.xlab.pci.doctor.module.bp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.recyclerview.adapter.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.BloodPressureItemBinding;
import com.wonders.xlab.pci.doctor.module.bp.bean.BPBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/2/22.
 */
public class BPRVAdapter extends SimpleRVAdapter<BPBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_pressure_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mBinding.setBp(getBean(position));
    }

    @Override
    public long getHeaderId(int position) {
        return getBean(position).getHeaderId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View titleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_time, parent, false);
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
        viewHolder.bpTitle.setText(time);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        BloodPressureItemBinding mBinding;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mBinding = BloodPressureItemBinding.bind(itemView);
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_header_time)
        TextView bpTitle;

        public TitleViewHolder(View titleView) {
            super(titleView);
            ButterKnife.bind(this, titleView);
        }
    }
}