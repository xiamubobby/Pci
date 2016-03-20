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
import com.wonders.xlab.patient.module.healthreport.adapter.BPReportAdapter;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BPReportBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * 今日血压
 */
public class BPReportFragment extends BaseFragment {

    @Bind(R.id.text_blood_pressure_report_ideal_range)
    TextView mTextIdealRange;
    @Bind(R.id.recycler_view_blood_pressure_report)
    RecyclerView mRecyclerView;

    private BPReportAdapter mBPReportAdapter;

    public BPReportFragment() {
        // Required empty public constructor
    }

    public static BPReportFragment newInstance() {
        return new BPReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bp_report_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(),getResources().getColor(R.color.divider),1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (null == mBPReportAdapter) {
            mBPReportAdapter = new BPReportAdapter();
        }

        List<BPReportBean> bpReportBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BPReportBean bean = new BPReportBean();
            bean.setId(String.valueOf(i));
            bean.setHeartRate(String.valueOf(i+70));
            bean.setHeartRateStatus(1);
            bean.setHighPressure(String.valueOf(i+110));
            bean.setHighPressureStatus(0);
            bean.setLowPressure(String.valueOf(i+60));
            bean.setLowPressureStatus(-1);
            bean.setMeasureTime(Calendar.getInstance().getTimeInMillis());

            bpReportBeanList.add(bean);
        }
        mBPReportAdapter.setDatas(bpReportBeanList);
        mRecyclerView.setAdapter(mBPReportAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
