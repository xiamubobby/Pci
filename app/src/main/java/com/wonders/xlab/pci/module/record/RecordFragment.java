package com.wonders.xlab.pci.module.record;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.record.monitor.HealthDataActivity;
import com.wonders.xlab.pci.module.record.userinfo.UserInfoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends BaseFragment {

    @Bind(R.id.tl_record)
    TabLayout mTlRecord;
    @Bind(R.id.vp_record)
    ViewPager mVpRecord;

    private FragmentVPAdapter mVPAdapter;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVPAdapter = new FragmentVPAdapter(getFragmentManager());
        //TODO 删除
//        mVPAdapter.addFragment(new HealthDataActivity(),"身体体征");
        mVPAdapter.addFragment(new HealthPlanFragment(),"健康方案");
        mVPAdapter.addFragment(new UserInfoFragment(),"基本信息");
        mVpRecord.setAdapter(mVPAdapter);
        mTlRecord.setupWithViewPager(mVpRecord);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
