package com.wonders.xlab.patient.module.healthreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.manager.SPManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.module.healthrecord.bp.BPAddActivity;
import com.wonders.xlab.patient.module.healthrecord.bp.BPGuideActivity;
import com.wonders.xlab.patient.module.healthrecord.bs.BSAddActivity;
import com.wonders.xlab.patient.module.healthrecord.bs.BSGuideActivity;
import com.wonders.xlab.patient.module.healthrecord.symptom.SymptomActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyRecordActivity extends AppbarActivity {

    @Bind(R.id.fam_daily_task)
    FloatingActionsMenu mFamDailyTask;
    @Bind(R.id.view_pager_daily_record)
    ViewPager mViewPager;
    @Bind(R.id.tab_daily_record)
    CommonTabLayout mTab;

    @Override
    public int getContentLayout() {
        return R.layout.daily_record_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "每日记录";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setupTopTab();
    }

    private void setupTopTab() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("我的医生", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("所有医生", R.drawable.tab_contact_select, R.drawable.tab_contact_unselect));
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

    @OnClick(R.id.fam_daily_task_bp)
    public void onRecordBpClick() {
        boolean useEquipment = SPManager.get(this).getBoolean(getString(R.string.pref_key_use_equipment), false);
        if (useEquipment) {
            recordNewData(BPGuideActivity.class);
        } else {
            recordNewData(BPAddActivity.class);
        }
    }

    @OnClick(R.id.fam_daily_task_bs)
    public void onRecordBsClick() {
        boolean useEquipment = SPManager.get(this).getBoolean(getString(R.string.pref_key_use_equipment), false);
        if (useEquipment) {
            recordNewData(BSGuideActivity.class);
        } else {
            recordNewData(BSAddActivity.class);
        }
    }

    @OnClick(R.id.fam_daily_task_symptom)
    public void onRecordSymptomClick() {
        recordNewData(SymptomActivity.class);
    }

    private void recordNewData(Class targetActivity) {
        startActivity(new Intent(this, targetActivity));
        mFamDailyTask.collapse();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
