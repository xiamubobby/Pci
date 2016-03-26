package com.wonders.xlab.pci.module.task.bs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.task.bp.otto.GuideOtto;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        View view = inflater.inflate(R.layout.fragment_bs_step_one, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_measure_bs_guide_0_next)
    public void next() {
        OttoManager.post(new GuideOtto(0));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
