package com.wonders.xlab.patient.module.healthreport;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.healthreport.adapter.BSReportAdapter;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BSReportBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * 今日血糖
 */
public class BSReportFragment extends BaseFragment {


    @Bind(R.id.tv_bs_report_ideal_range)
    TextView mTvIdealRange;
    @Bind(R.id.recycler_view_blood_sugar_report)
    RecyclerView mRecyclerView;

    private BSReportAdapter mBSReportAdapter;

    public BSReportFragment() {
        // Required empty public constructor
    }

    public static BSReportFragment newInstance() {
        return new BSReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bs_report_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));

        if (null == mBSReportAdapter) {
            mBSReportAdapter = new BSReportAdapter();
        }

        List<BSReportBean> bsReportBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BSReportBean bean = new BSReportBean();
            bean.setId(String.valueOf(i));
            bean.setBloodSugar(String.valueOf(0.4 * i));
            bean.setBloodSugarStatus(1);
            bean.setMeasurePeriod("晚餐前");
            bean.setMeasureTime(Calendar.getInstance().getTimeInMillis());

            bsReportBeanList.add(bean);
        }
        mBSReportAdapter.setDatas(bsReportBeanList);
        mRecyclerView.setAdapter(mBSReportAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
