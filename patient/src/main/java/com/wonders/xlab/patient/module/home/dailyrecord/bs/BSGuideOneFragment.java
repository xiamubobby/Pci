package com.wonders.xlab.patient.module.home.dailyrecord.bs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.home.dailyrecord.bp.otto.GuideOtto;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BSGuideOneFragment extends BaseFragment {

    @Bind(R.id.btn_measure_bs_guide_0_next)
    Button mBtnNext;

    public BSGuideOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bs_measure_guide_one_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_measure_bs_guide_0_next)
    public void next() {
        OttoManager.post(new GuideOtto(0));
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("血糖测量(引导一)");
        MobclickAgent.onResume(getActivity());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("血糖测量(引导一)");
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
