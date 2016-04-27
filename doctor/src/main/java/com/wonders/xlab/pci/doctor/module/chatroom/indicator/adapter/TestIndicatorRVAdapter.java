package com.wonders.xlab.pci.doctor.module.chatroom.indicator.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.chatroom.indicator.adapter.bean.TestIndicatorBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jimmy on 16/4/27.
 */
public class TestIndicatorRVAdapter extends SimpleRVAdapter<TestIndicatorBean> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_indicator_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        TestIndicatorBean bean = getBean(position);
        viewHolder.tvDepartmentName.setText("科室名称："+bean.getDepartmentsName().get());
        viewHolder.tvTime.setText("检验时间："+bean.getTestTimeInStr().get());
        viewHolder.tvHospitalName.setText("医院名称："+bean.getHospitalName().get());
        IndicatorListRVAdapter rvAdapter = new IndicatorListRVAdapter();
        rvAdapter.setDatas(bean.getIndicatorList().get());

        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder.itemView.getContext(),LinearLayoutManager.VERTICAL,false));
        viewHolder.recyclerView.setAdapter(rvAdapter);


    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_test_indicator_item_time)
        TextView tvTime;
        @Bind(R.id.tv_test_indicator_item_hospital_name)
        TextView tvHospitalName;
        @Bind(R.id.recycler_view_test_indicator_item)
        RecyclerView recyclerView;
        @Bind(R.id.tv_test_indicator_item_department_name)
        TextView tvDepartmentName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class IndicatorListRVAdapter extends SimpleRVAdapter<TestIndicatorBean.IndicatorBean> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new IndicatorItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_indicator_sub_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            IndicatorItemViewHolder viewHolder = (IndicatorItemViewHolder) holder;
            TestIndicatorBean.IndicatorBean bean = getBean(position);
            viewHolder.tvItem.setText(bean.getItem());
            viewHolder.tvValue.setText(bean.getReferenceValue());
            viewHolder.tvResult.setText(bean.getResult());

        }

        class IndicatorItemViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_test_indicator_sub_item)
            TextView tvItem;
            @Bind(R.id.tv_test_indicator_sub_result)
            TextView tvResult;
            @Bind(R.id.tv_test_indicator_sub_value)
            TextView tvValue;

            public IndicatorItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
