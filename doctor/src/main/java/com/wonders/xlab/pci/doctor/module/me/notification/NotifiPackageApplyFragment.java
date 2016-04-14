package com.wonders.xlab.pci.doctor.module.me.notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;

import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifiPackageApplyFragment extends BaseFragment {


    public NotifiPackageApplyFragment() {
        // Required empty public constructor
    }

    public static NotifiPackageApplyFragment getInstance() {
        return new NotifiPackageApplyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.package_apply_fragment, container, false);
    }

}
