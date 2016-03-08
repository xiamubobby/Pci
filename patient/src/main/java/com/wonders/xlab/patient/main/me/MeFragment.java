package com.wonders.xlab.patient.main.me;


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
public class MeFragment extends BaseFragment {


    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment getInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.me_fragment, container, false);
    }

}
