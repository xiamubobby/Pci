package com.wonders.xlab.pci.module;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.wonders.xlab.common.viewpager.XViewPager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.pci.module.home.HomeFragment;
import com.wonders.xlab.pci.module.record.RecordFragment;
import com.wonders.xlab.pci.module.task.DailyTaskActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.vp_main)
    XViewPager mVpMain;
    @Bind(R.id.fab_main)
    FloatingActionButton mFab;
    @Bind(R.id.tl_main)
    TabLayout mTabLayout;
    @Bind(R.id.tv_main_my_doctor)
    TextView mTvMyDoctor;

    private FragmentVPAdapter mFragmentVPAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentVPAdapter = new FragmentVPAdapter(getFragmentManager());
        mFragmentVPAdapter.addFragment(new HomeFragment());
        mFragmentVPAdapter.addFragment(new RecordFragment());
        mVpMain.setOffscreenPageLimit(2);
        mVpMain.setAdapter(mFragmentVPAdapter);

        initBottomTab();

        RxView.clicks(mFab)
                .throttleFirst(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                    }
                });
    }

    private void initBottomTab() {
        mTabLayout.setupWithViewPager(mVpMain);
        mTabLayout.removeAllTabs();

        mTabLayout.addTab(mTabLayout.newTab().setText("首页"));
        mTabLayout.addTab(mTabLayout.newTab().setText("档案"));

        String tmp = "我的医生";
        SpannableString sp = new SpannableString(tmp);
        //设置斜体
        sp.setSpan(new StyleSpan(Typeface.ITALIC), 0, tmp.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvMyDoctor.setText(sp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_report_detail clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_main_task) {
            startActivity(new Intent(this, DailyTaskActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
