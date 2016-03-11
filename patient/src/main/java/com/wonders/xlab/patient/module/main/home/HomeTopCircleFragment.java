package com.wonders.xlab.patient.module.main.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.main.home.bean.HomeTopCircleBean;

import java.util.ArrayList;

import im.hua.library.base.BaseFragment;

public class HomeTopCircleFragment extends BaseFragment {
    private static final String ARG_VALUE_LIST = "valueList";

    private ArrayList<HomeTopCircleBean> mValueList;

    public HomeTopCircleFragment() {
        // Required empty public constructor
    }

    public static HomeTopCircleFragment newInstance(ArrayList<HomeTopCircleBean> valueList) {
        HomeTopCircleFragment fragment = new HomeTopCircleFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_VALUE_LIST, valueList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mValueList = getArguments().getParcelableArrayList(ARG_VALUE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_top_circle_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
