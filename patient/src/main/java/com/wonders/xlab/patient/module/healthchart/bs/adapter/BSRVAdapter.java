package com.wonders.xlab.patient.module.healthchart.bs.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.databinding.BsHrItemBinding;
import com.wonders.xlab.patient.module.healthchart.bs.bean.BSBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/23.
 */
public class BSRVAdapter extends SimpleRVAdapter<BSBean> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {


    private void reunionList() {
        List<BSBean> beanList = getBeanList();
        Collections.sort(beanList, new Comparator<BSBean>() {
            @Override
            public int compare(BSBean lhs, BSBean rhs) {
                long l = lhs.getRecordTimeInMill();
                long r = rhs.getRecordTimeInMill();
                return l < r ? 1 : (l == r ? 0 : -1);
            }
        });
        long headerId = 0;
        for (int i = 0; i < beanList.size(); i++) {
            if (0 != i) {
                if (!DateUtil.isTheSameMonth(beanList.get(i - 1).getRecordTimeInMill(), beanList.get(i).getRecordTimeInMill())) {
                    headerId++;
                }
            }
            beanList.get(i).setHeaderId(headerId);
        }
        notifyDataSetChanged();

    }

    @Override
    public void setDatas(List<BSBean> mBeanList) {
        super.setDatas(mBeanList);
        reunionList();
    }

    @Override
    public void appendDatas(List<BSBean> mBeanList) {
        super.appendDatas(mBeanList);
        reunionList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bs_hr_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        BSBean bean = getBean(position);
        viewHolder.mBinding.setBs(bean);

        if (bean.getBeforeDawnStandard() == 0) {
            viewHolder.beforeSleepBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorAccent));
        } else if (bean.getBeforeDawnStandard() == 2) {
            viewHolder.beforeSleepBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.appYellow));
        }
        if (bean.getBreakfastAfterStandard() == 0) {
            viewHolder.breakfastAfterBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorAccent));
        } else if (bean.getBreakfastAfterStandard() == 2) {
            viewHolder.breakfastAfterBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.appYellow));
        }
        if (bean.getBreakfastBeforeStandard() == 0) {
            viewHolder.breakfastBeforeBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorAccent));
        } else if (bean.getBreakfastBeforeStandard() == 2) {
            viewHolder.breakfastBeforeBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.appYellow));
        }
        if (bean.getDinnerAfterStandard() == 0) {
            viewHolder.dinnerAfterBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorAccent));
        } else if (bean.getDinnerAfterStandard() == 2) {
            viewHolder.dinnerAfterBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.appYellow));
        }
        if (bean.getDinnerBeforeStandard() == 0) {
            viewHolder.dinnerBeforeBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorAccent));
        } else if (bean.getDinnerBeforeStandard() == 2) {
            viewHolder.dinnerBeforeBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.appYellow));
        }
        if (bean.getLunchAfterStandard() == 0) {
            viewHolder.lunchAfterBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorAccent));
        } else if (bean.getLunchAfterStandard() == 2) {
            viewHolder.lunchAfterBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.appYellow));
        }
        if (bean.getLunchBeforeStandard() == 0) {
            viewHolder.lunchBeforeBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorAccent));
        } else if (bean.getLunchBeforeStandard() == 2) {
            viewHolder.lunchBeforeBS.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.appYellow));
        }

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
        long time = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (getBean(i).getHeaderId() == getHeaderId(position)) {
                time = getBean(i).getRecordTimeInMill();
                break;
            }
        }
        viewHolder.bpTitle.setText(DateUtil.format(time, "yyyy/MM"));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.breakfastBeforeBS)
        TextView breakfastBeforeBS;
        @Bind(R.id.breakfastAfterBS)
        TextView breakfastAfterBS;
        @Bind(R.id.lunchBeforeBS)
        TextView lunchBeforeBS;
        @Bind(R.id.lunchAfterBS)
        TextView lunchAfterBS;
        @Bind(R.id.dinnerBeforeBS)
        TextView dinnerBeforeBS;
        @Bind(R.id.dinnerAfterBS)
        TextView dinnerAfterBS;
        @Bind(R.id.beforeSleepBS)
        TextView beforeSleepBS;

        BsHrItemBinding mBinding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBinding = BsHrItemBinding.bind(itemView);
            ButterKnife.bind(this, itemView);
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
