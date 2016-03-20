package com.wonders.xlab.patient.module.healthreport;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.patient.R;

import im.hua.library.base.BaseFragment;

/**
 * 今日血糖
 */
public class BSReportFragment extends BaseFragment {


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
        return inflater.inflate(R.layout.bs_report_fragment, container, false);
    }

}
