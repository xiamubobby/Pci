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
public class PackageApplyFragment extends BaseFragment {


    public PackageApplyFragment() {
        // Required empty public constructor
    }

    public static PackageApplyFragment getInstance() {
        return new PackageApplyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.package_apply_fragment, container, false);
    }

}
