package com.wonders.xlab.pci.module.record.monitor;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonitorFragment extends BaseFragment {


    public MonitorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.tv_monitor_bp)
    public void onBPClick() {
        startActivity(new Intent(getActivity(),BPActivity.class));
    }

    @OnClick(R.id.tv_monitor_symptom)
    public void onSymptomClick() {
        startActivity(new Intent(getActivity(),SymptomActivity.class));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
