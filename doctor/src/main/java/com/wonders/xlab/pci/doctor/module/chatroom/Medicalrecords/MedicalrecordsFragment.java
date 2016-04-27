package com.wonders.xlab.pci.doctor.module.chatroom.Medicalrecords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;

import im.hua.library.base.BaseFragment;

/**
 * Created by jimmy on 16/4/27.
 */
public class MedicalrecordsFragment extends BaseFragment {

    public static MedicalrecordsFragment newInstance(String patientId) {
        MedicalrecordsFragment fragment = new MedicalrecordsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.medical_records_fragment, container, false);
        return view;
    }
}
