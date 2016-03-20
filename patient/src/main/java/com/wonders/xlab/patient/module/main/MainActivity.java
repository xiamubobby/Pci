package com.wonders.xlab.patient.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.squareup.otto.Subscribe;
import com.umeng.update.UmengUpdateAgent;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.auth.login.LoginActivity;
import com.wonders.xlab.patient.module.main.doctors.DoctorFragment;
import com.wonders.xlab.patient.module.main.home.HomeFragment;
import com.wonders.xlab.patient.module.main.me.MeFragment;
import com.wonders.xlab.patient.otto.ForceExitOtto;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseActivity;
import im.hua.utils.NotifyUtil;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.view_pager_main)
    ViewPager mViewPagerMain;
    @Bind(R.id.tab_main_bottom)
    CommonTabLayout mTabMainBottom;

    private FragmentVPAdapter mFragmentVPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OttoManager.register(this);
        if (!AIManager.getInstance(this).hasLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mFragmentVPAdapter.addFragment(HomeFragment.getInstance());
        mFragmentVPAdapter.addFragment(DoctorFragment.getInstance());
        mFragmentVPAdapter.addFragment(MeFragment.getInstance());
        mViewPagerMain.setOffscreenPageLimit(3);
        mViewPagerMain.setAdapter(mFragmentVPAdapter);

        setupBottomTab();

        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
    }

    private void setupBottomTab() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("患者", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("医生", R.drawable.tab_contact_select, R.drawable.tab_contact_unselect));
                    break;
                case 2:
                    tabEntities.add(new TabEntity("我", R.drawable.tab_me_selected, R.drawable.tab_me_unselected));
                    break;
            }
        }
        mTabMainBottom.setTabData(tabEntities);
        mTabMainBottom.setOnTabSelectListener(new OnTabSelectListener() {
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
                mTabMainBottom.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
    }

    /**
     * 优雅的退出全部Activity
     * @param bean
     */
    @Subscribe
    public void forceExit(ForceExitOtto bean) {
        new NotifyUtil().cancelAll(this);
        AIManager.getInstance(this).exit();

        startActivity(new Intent(this, MainActivity.class));
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
