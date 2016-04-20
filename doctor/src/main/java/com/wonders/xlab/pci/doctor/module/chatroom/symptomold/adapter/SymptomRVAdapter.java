package com.wonders.xlab.pci.doctor.module.chatroom.symptomold.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.SymptomItemBinding;
import com.wonders.xlab.pci.doctor.module.chatroom.symptomold.bean.SymptomBean;

/**
 * Created by hua on 16/2/23.
 */
public class SymptomRVAdapter extends SimpleRVAdapter<SymptomBean> {
    private OnCommentClickListener mOnCommentClickListener;

    public void setOnCommentClickListener(OnCommentClickListener onCommentClickListener) {
        mOnCommentClickListener = onCommentClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mBinding.setSymptom(getBean(position));
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        SymptomItemBinding mBinding;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mBinding = SymptomItemBinding.bind(itemView);
            mBinding.setHandler(this);
        }

        public void onBtnClick(View view) {
            if (null != mOnCommentClickListener) {
                mOnCommentClickListener.onBtnClick(getBean(getAdapterPosition()));
            }
        }

        public void onCommentClick(View view) {
            if (null != mOnCommentClickListener) {
                mOnCommentClickListener.onCommentClick(getBean(getAdapterPosition()));
            }
        }
    }

    public interface OnCommentClickListener{
        void onBtnClick(SymptomBean bean);

        void onCommentClick(SymptomBean bean);
    }
}
