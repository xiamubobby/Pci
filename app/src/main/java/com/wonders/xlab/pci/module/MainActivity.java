package com.wonders.xlab.pci.module;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.wonders.xlab.common.viewpager.XViewPager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.RxBus;
import com.wonders.xlab.pci.module.base.BaseActivity;
import com.wonders.xlab.pci.module.home.HomeFragment;
import com.wonders.xlab.pci.module.login.LoginActivity;
import com.wonders.xlab.pci.module.record.RecordFragment;
import com.wonders.xlab.pci.module.rxbus.ExitBus;
import com.wonders.xlab.pci.module.task.DailyTaskActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity {

    @Bind(R.id.vp_main)
    XViewPager mVpMain;
    @Bind(R.id.fab_main_my_doctor)
    FloatingActionButton mFab;
    @Bind(R.id.tl_main)
    TabLayout mTabLayout;
    @Bind(R.id.tv_main_my_doctor)
    TextView mTvMyDoctor;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.ll_main_my_doctor)
    LinearLayout mLlMainMyDoctor;

    private FragmentVPAdapter mFragmentVPAdapter;
    private CompositeSubscription mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!AIManager.getInstance(this).hasLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserCenterActivity.class));
            }
        });

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mFragmentVPAdapter.addFragment(new HomeFragment());
        mFragmentVPAdapter.addFragment(new RecordFragment());
        mVpMain.setAdapter(mFragmentVPAdapter);
        initRxBus();
        initBottomTab();

        RxView.clicks(mLlMainMyDoctor)
                .throttleFirst(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(new Intent(MainActivity.this, MyDoctorActivity.class));
                    }
                });
        RxView.clicks(mFab)
                .throttleFirst(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(new Intent(MainActivity.this, MyDoctorActivity.class));
                    }
                });
    }

    private void initRxBus() {
        mSubscription = new CompositeSubscription();

        mSubscription.add(RxBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof ExitBus) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    MainActivity.this.finish();
                }
            }
        }));
    }

    private void initBottomTab() {
        mTabLayout.setupWithViewPager(mVpMain);
        mTabLayout.removeAllTabs();
        LayoutInflater inflater = LayoutInflater.from(this);

        View home = inflater.inflate(R.layout.tab_main_home, mTabLayout, false);
        View record = inflater.inflate(R.layout.tab_main_record, mTabLayout, false);

        mTabLayout.addTab(mTabLayout.newTab().setCustomView(home));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(record));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_main);
                TextView textView = (TextView) view.findViewById(R.id.tv_tab_main);
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                switch (tab.getPosition()) {
                    case 0:
                        mVpMain.setCurrentItem(0);
                        imageView.setImageResource(R.drawable.ic_home_pressed);
                        break;
                    case 1:
                        mVpMain.setCurrentItem(1);
                        imageView.setImageResource(R.drawable.ic_record_pressed);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_main);
                TextView textView = (TextView) view.findViewById(R.id.tv_tab_main);
                textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
                switch (tab.getPosition()) {
                    case 0:
                        imageView.setImageResource(R.drawable.ic_home_normal);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.ic_record_normal);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        String tmp = "我的医生";
        SpannableString sp = new SpannableString(tmp);
        //设置斜体
        sp.setSpan(new StyleSpan(Typeface.ITALIC), 0, tmp.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvMyDoctor.setText(sp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_main_task) {
            startActivity(new Intent(this, DailyTaskActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
