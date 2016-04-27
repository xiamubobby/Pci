package com.wonders.xlab.pci.doctor.module.chatroom.surgicalhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;

import im.hua.library.base.BaseFragment;

/**
 * Created by jimmy on 16/4/27.
 * 住院手术室
 */
public class SurgicalHistoryFragment extends BaseFragment {

    public static SurgicalHistoryFragment newInstance(String patientId) {
        SurgicalHistoryFragment fragment = new SurgicalHistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.surgical_history_fragment, container, false);
        return view;
    }
}
