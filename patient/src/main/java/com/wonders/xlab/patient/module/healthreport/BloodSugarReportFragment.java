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
public class BloodSugarReportFragment extends BaseFragment {


    public BloodSugarReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.blood_sugar_fragment, container, false);
    }

}
