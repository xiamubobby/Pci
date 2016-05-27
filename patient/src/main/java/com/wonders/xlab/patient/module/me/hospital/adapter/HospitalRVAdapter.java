package com.wonders.xlab.patient.module.me.hospital.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/5/27.
 */
public class HospitalRVAdapter extends SimpleRVAdapter<HospitalBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((ItemViewHolder) holder).mTextView.setText(getBean(position).getName());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textView)
        TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
