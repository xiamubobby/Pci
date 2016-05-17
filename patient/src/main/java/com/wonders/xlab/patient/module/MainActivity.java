package com.wonders.xlab.patient.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.auth.login.LoginActivity;
import com.wonders.xlab.patient.module.home.HomeFragment;
import com.wonders.xlab.patient.module.me.MeFragment;
import com.wonders.xlab.patient.module.mydoctor.DoctorMyFragment;
import com.wonders.xlab.patient.module.otto.MainBottomUnreadNotifyCountOtto;
import com.wonders.xlab.patient.module.service.ServiceFragment;
import com.wonders.xlab.patient.otto.ForceExitOtto;
import com.wonders.xlab.patient.otto.MeNotifyCountOtto;
import com.wonders.xlab.patient.service.XEMChatService;
import com.wonders.xlab.patient.util.UnReadMessageUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseActivity;
import im.hua.utils.NotifyUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 包含整个APP的入口，容纳Viewpager
 */
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
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        OttoManager.register(this);
        if (!AIManager.getInstance().hasLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        if (((XApplication) getApplication()).showSplash()) {
            startActivity(new Intent(this, SplashActivity.class));
        }

        setContentView(R.layout.main_activity);
        ButterKnife.bind(MainActivity.this);

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mFragmentVPAdapter.addFragment(HomeFragment.getInstance());
        mFragmentVPAdapter.addFragment(DoctorMyFragment.getInstance());
        mFragmentVPAdapter.addFragment(ServiceFragment.getInstance());
        mFragmentVPAdapter.addFragment(MeFragment.getInstance());
        mViewPagerMain.setOffscreenPageLimit(4);
        mViewPagerMain.setAdapter(mFragmentVPAdapter);

        setupBottomTab();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Observable.just(null)
                .delaySubscription(5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        ((XApplication)getApplication()).getComponent().getAlarmUtil().scheduleMedicineRemindAlarm(MainActivity.this);
                        startService(new Intent(MainActivity.this, XEMChatService.class));
                    }
                });
    }

    private void setupBottomTab() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("首页", R.drawable.tab_home_selected, R.drawable.tab_home_unselect));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("医生", R.drawable.tab_doctor_selected, R.drawable.tab_doctor_unselect));
                    break;
                case 2:
                    tabEntities.add(new TabEntity("服务", R.drawable.tab_service_selected, R.drawable.tab_service_unselect));
                    break;
                case 3:
                    tabEntities.add(new TabEntity("我", R.drawable.tab_me_selected, R.drawable.tab_me_unselect));
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

        int unreadMessageCounts = UnReadMessageUtil.getAllUnreadMessageCounts();
        if (unreadMessageCounts > 0) {
            mTabMainBottom.showMsg(1, unreadMessageCounts);
        }
        mTabMainBottom.setMsgMargin(2, -5, 8);
        mTabMainBottom.setMsgMargin(1, -5, 8);

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

    @Subscribe
    public void changeDoctorNotifyCounts(MainBottomUnreadNotifyCountOtto otto) {
        int counts = UnReadMessageUtil.getAllUnreadMessageCounts();
        if (counts > 0) {
            mTabMainBottom.showMsg(1, counts);
            mTabMainBottom.setMsgMargin(1, -5, 8);
        } else {
            mTabMainBottom.hideMsg(1);
        }
    }

    @Subscribe
    public void changeMeNotifyCounts(MeNotifyCountOtto otto) {
        mTabMainBottom.showDot(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
    }

    /**
     * 优雅的退出全部Activity
     *
     * @param bean
     */
    @Subscribe
    public void forceExit(ForceExitOtto bean) {
        NotifyUtil.cancelAll(this);
        AIManager.getInstance().logout();

        startActivity(new Intent(this, MainActivity.class));
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onResume() {
        super.onResume();
        UmengUpdateAgent.update(MainActivity.this);
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
