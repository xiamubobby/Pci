package com.wonders.xlab.patient.module.main.doctors;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.patient.R;

import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDoctorFragment extends BaseFragment {


    public MyDoctorFragment() {
        // Required empty public constructor
    }

    public static MyDoctorFragment newInstance() {
        return new MyDoctorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.doctor_my_fragment, container, false);
    }

}
