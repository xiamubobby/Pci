package com.wonders.xlab.patient.module.healthreport;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.healthreport.bp.BPHRFragment;
import com.wonders.xlab.patient.module.healthreport.bs.BSHRFragment;
import com.wonders.xlab.patient.module.healthreport.symptom.SymptomHRFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HealthReportActivity extends AppbarActivity {

    @Bind(R.id.view_pager_health_report)
    ViewPager mViewPager;
    @Bind(R.id.tab_health_report)
    CommonTabLayout mTab;
    private FragmentVPAdapter mFragmentVPAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.health_report_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "健康报表";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mFragmentVPAdapter.addFragment(BSHRFragment.newInstance(), "血糖");
        mFragmentVPAdapter.addFragment(BPHRFragment.newInstance(), "血压");
        mFragmentVPAdapter.addFragment(SymptomHRFragment.newInstance(), "不适症状");
        mViewPager.setAdapter(mFragmentVPAdapter);

        //2
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("血糖", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("血压", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
                    break;
                case 2:
                    tabEntities.add(new TabEntity("不适症状", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
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
        ButterKnife.unbind(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
