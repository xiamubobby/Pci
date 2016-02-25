package com.wonders.xlab.pci.doctor.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.umeng.update.UmengUpdateAgent;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.login.LoginActivity;
import com.wonders.xlab.pci.doctor.module.me.MeFragment;
import com.wonders.xlab.pci.doctor.module.patient.PatientFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Bind(R.id.view_pager_main)
    ViewPager mViewPagerMain;
    @Bind(R.id.tab_main)
    CommonTabLayout mTabMain;

    private FragmentVPAdapter mVPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);

        if (!AIManager.getInstance(this).hasLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            setContentView(R.layout.main_activity);
            ButterKnife.bind(this);

            UmengUpdateAgent.update(this);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            initViewPager();
        }
    }

    private void initViewPager() {
        mVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mVPAdapter.addFragment(PatientFragment.newInstance(), "患者");
        mVPAdapter.addFragment(MeFragment.newInstance(), "我");
        mViewPagerMain.setAdapter(mVPAdapter);

        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("患者", R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("我", R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                    break;
            }
        }
        mTabMain.setTabData(tabEntities);
        mTabMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPagerMain.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabMain.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
