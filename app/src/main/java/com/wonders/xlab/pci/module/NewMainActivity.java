package com.wonders.xlab.pci.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.BaseActivity;
import com.wonders.xlab.pci.module.home.NewHomeFragment;
import com.wonders.xlab.pci.module.home.bean.TabEntity;
import com.wonders.xlab.pci.module.login.LoginActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewMainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.vp_new_main)
    ViewPager mVpNewMain;
    @Bind(R.id.tab_new_main)
    CommonTabLayout mTabNewMain;

    private FragmentVPAdapter mVPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);

        if (!AIManager.getInstance(this).hasLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_new_main);
        ButterKnife.bind(this);

        initAdapter();
    }

    private void initAdapter() {
        if (mVPAdapter == null) {
            mVPAdapter = new FragmentVPAdapter(getFragmentManager());
        }
        NewHomeFragment homeFragment = new NewHomeFragment();

        mVPAdapter.addFragment(homeFragment, "首页");

        mVpNewMain.setAdapter(mVPAdapter);

        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("首页", R.drawable.ic_home_pressed, R.drawable.ic_home_normal));
                    break;
            }
        }
        mTabNewMain.setTabData(tabEntities);
        mTabNewMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVpNewMain.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mVpNewMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabNewMain.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
