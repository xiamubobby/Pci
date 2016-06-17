package com.wonders.xlab.patient.module.healthchart.bp.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.BpHrItemBinding;
import com.wonders.xlab.patient.module.healthchart.bp.bean.BPListBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/22.
 */
public class BPRVAdapter extends SimpleRVAdapter<BPListBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    /**
     * 排序并且设置headerId，用于分组
     */
    private void reunionList() {
        List<BPListBean> beanList = getBeanList();
        Collections.sort(beanList, new Comparator<BPListBean>() {
            @Override
            public int compare(BPListBean lhs, BPListBean rhs) {
                long l = lhs.getRecordTimeInMill();
                long r = rhs.getRecordTimeInMill();
                return l < r ? 1 : (l == r ? 0 : -1);
            }
        });
        long headerId = 0;
        for (int i = 0; i < beanList.size(); i++) {
            if (0 != i) {
                if (!DateUtil.isTheSameDay(beanList.get(i - 1).getRecordTimeInMill(), beanList.get(i).getRecordTimeInMill())) {
                    headerId++;
                }
            }
            beanList.get(i).setHeaderId(headerId);
        }
        notifyDataSetChanged();

    }

    @Override
    public void setDatas(List<BPListBean> mBeanList) {
        super.setDatas(mBeanList);
        reunionList();
    }

    @Override
    public void appendDatas(List<BPListBean> mBeanList) {
        super.appendDatas(mBeanList);
        reunionList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bp_hr_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mBinding.setBp(getBean(position));
        BPListBean bean = getBean(position);
        Drawable drawableUp = ContextCompat.getDrawable(viewHolder.itemView.getContext(),R.drawable.pic_arrow_up);
        if (drawableUp != null) {
            drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        }
        Drawable drawableDown = ContextCompat.getDrawable(viewHolder.itemView.getContext(),R.drawable.pic_arrow_down);
        if (drawableDown != null) {
            drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
        }


        if (bean.getDiastolicStandard() == 0) {
            viewHolder.diastolic.setCompoundDrawables(null, null, drawableDown, null);
        } else if (bean.getDiastolicStandard() == 2) {
            viewHolder.diastolic.setCompoundDrawables(null, null, drawableUp, null);
        } else {
            viewHolder.diastolic.setCompoundDrawables(null, null, null, null);
        }
        if (bean.getHeartStandard() == 0) {
            viewHolder.heartRate.setCompoundDrawables(null, null, drawableDown, null);
        } else if (bean.getHeartStandard() == 2) {
            viewHolder.heartRate.setCompoundDrawables(null, null, drawableUp, null);
        } else {
            viewHolder.heartRate.setCompoundDrawables(null, null, null, null);
        }
        if (bean.getSystolicStandard() == 0) {
            viewHolder.systolic.setCompoundDrawables(null, null, drawableDown, null);
        } else if (bean.getSystolicStandard() == 2) {
            viewHolder.systolic.setCompoundDrawables(null, null, drawableUp, null);
        } else {
            viewHolder.systolic.setCompoundDrawables(null, null, null, null);
        }

    }

    @Override
    public long getHeaderId(int position) {
        return getBean(position).getHeaderId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View titleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bp_health_report_header_time_item, parent, false);
        return new TitleViewHolder(titleView);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TitleViewHolder viewHolder = (TitleViewHolder) holder;
        long time = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (getBean(i).getHeaderId() == getHeaderId(position)) {
                time = getBean(i).getRecordTimeInMill();
                break;
            }
        }
        viewHolder.bpTitle.setText(DateUtil.format(time, DateUtil.DEFAULT_FORMAT_DAY));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.systolic)
        TextView systolic;
        @Bind(R.id.diastolic)
        TextView diastolic;
        @Bind(R.id.heartRate)
        TextView heartRate;

        BpHrItemBinding mBinding;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBinding = BpHrItemBinding.bind(itemView);
            ButterKnife.bind(this, itemView);
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
