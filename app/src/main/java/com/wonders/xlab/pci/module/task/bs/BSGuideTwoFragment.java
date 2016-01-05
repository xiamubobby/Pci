package com.wonders.xlab.pci.module.task.bs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.task.bp.otto.GuideOtto;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BSGuideTwoFragment extends BaseFragment {

    public BSGuideTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bs_step_two, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
    }

    @OnClick(R.id.btn_measure_bs_guide_1_start)
    public void start() {
        OttoManager.post(new GuideOtto(1));
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("血糖测量(引导二)");
        MobclickAgent.onResume(getActivity());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("血糖测量(引导二)");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
