package com.wonders.xlab.pci.doctor.module.chatroom.symptom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.databinding.SymptomReportItemBinding;
import com.wonders.xlab.pci.doctor.databinding.SymptomReportItemSymptomLabelBinding;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean.SymptomReportBean;
import com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean.SymptomReportLabelBean;
import com.zhy.view.flowlayout.FlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hua on 16/3/21.
 * daily report  -> symptom list
 */
public class SymptomAdapter extends SimpleRVAdapter<SymptomReportBean> {
    private OnCommentClickListener mOnCommentClickListener;

    public void setOnCommentClickListener(OnCommentClickListener onCommentClickListener) {
        mOnCommentClickListener = onCommentClickListener;
    }

    public interface OnCommentClickListener{
        void onBtnClick(SymptomReportBean bean);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_report_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        final SymptomReportBean bean = getBean(position);

        viewHolder.binding.setBean(bean);

        viewHolder.mFlSymptoms.removeAllViews();
        viewHolder.mBtnAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnCommentClickListener) {
                    mOnCommentClickListener.onBtnClick(bean);
                }
            }
        });
        LayoutInflater layoutInflater = LayoutInflater.from(viewHolder.itemView.getContext());
        for (SymptomReportLabelBean symptom : bean.getSymptomList()) {
            final View itemView = layoutInflater.inflate(R.layout.symptom_report_item_symptom_label, (ViewGroup) viewHolder.itemView, false);

            SymptomReportItemSymptomLabelBinding binding = SymptomReportItemSymptomLabelBinding.bind(itemView);
            binding.setBean(bean);
            binding.setLabelBean(symptom);

            viewHolder.mFlSymptoms.addView(itemView);
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_symptom_report_item_status)
        TextView mTvStatus;
        @Bind(R.id.tv_symptom_report_item_time)
        TextView mTvTime;
        @Bind(R.id.fl_symptom_report_item_symptoms)
        FlowLayout mFlSymptoms;
        @Bind(R.id.divider_symptom_above_advice)
        View mDivider;
        @Bind(R.id.btn_symptom_report_item_advice)
        Button mBtnAdvice;
        SymptomReportItemBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = SymptomReportItemBinding.bind(itemView);
        }
    }

}
