package com.wonders.xlab.patient.module.healthreport.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.healthreport.adapter.SymptomReportAdapter;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.SymptomReportBean;
import com.wonders.xlab.patient.module.healthreport.otto.SymptomSaveSuccessOtto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SymptomReportFragment extends BaseFragment {

    @Bind(R.id.recycler_view_symptom_report)
    RecyclerView mRecyclerView;

    private SymptomReportAdapter adapter;

    public SymptomReportFragment() {
        // Required empty public constructor
    }

    public static SymptomReportFragment newInstance() {
        return new SymptomReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.symptom_report_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 8));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (null == adapter) {
            List<SymptomReportBean> symptomReportBeanList = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                SymptomReportBean bean = new SymptomReportBean();
                bean.setId(String.valueOf(i));
                bean.setAdvice("医生建议");
                bean.setHasConfirmed(i % 3 == 0);
                bean.setRecordTimeInMill(Calendar.getInstance().getTimeInMillis());

                List<String> symptoms = new ArrayList<>();
                for (int j = 0; j < i + 1; j++) {
                    symptoms.add("症状" + j);
                }
                bean.setSymptomList(symptoms);

                symptomReportBeanList.add(bean);
            }

            adapter = new SymptomReportAdapter();
            adapter.setDatas(symptomReportBeanList);
        }

        mRecyclerView.setAdapter(adapter);

    }

    @Subscribe
    public void refresh(SymptomSaveSuccessOtto otto) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
