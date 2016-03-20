package com.wonders.xlab.patient.module.healthreport;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.patient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SymptomReportFragment extends Fragment {


    public SymptomReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.symptom_report_fragment, container, false);
    }

}
