package com.wonders.xlab.pci.doctor.module.me.notification;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationActivity extends AppbarActivity {

    @Bind(R.id.view_pager_notification)
    ViewPager mViewPager;

    private FragmentVPAdapter mFragmentVPAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.notification_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mFragmentVPAdapter.addFragment(GroupInviteFragment.getInstance(), "邀请通知");
        mFragmentVPAdapter.addFragment(PackageApplyFragment.getInstance(), "套餐申请通知");
        mFragmentVPAdapter.addFragment(OthersFragment.getInstance(), "其他通知");

        mViewPager.setAdapter(mFragmentVPAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentVPAdapter = null;
    }
}