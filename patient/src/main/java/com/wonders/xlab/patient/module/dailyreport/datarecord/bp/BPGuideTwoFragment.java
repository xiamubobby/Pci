package com.wonders.xlab.patient.module.dailyreport.datarecord.bp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.dailyreport.datarecord.bp.otto.GuideOtto;

import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BPGuideTwoFragment extends BaseFragment {

    public BPGuideTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bp_measure_guide_two_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OttoManager.register(this);
    }

    @OnClick(R.id.btn_measure_bp_guide_1_start)
    public void start() {
        OttoManager.post(new GuideOtto(1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_bp_guide_two));
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_bp_guide_two));
    }
}
