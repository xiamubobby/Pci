package com.wonders.xlab.pci.doctor.module.me.notification;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationActivity extends AppbarActivity {

    @Bind(R.id.view_pager_notification)
    ViewPager mViewPager;
    @Bind(R.id.tab_notification)
    CommonTabLayout mTab;

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

        //2
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("邀请通知", R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("套餐申请通知", R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    break;
                case 2:
                    tabEntities.add(new TabEntity("其他通知", R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    break;
            }
        }
        mTab.setTabData(tabEntities);
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentVPAdapter = null;
    }
}