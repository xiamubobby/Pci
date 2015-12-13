package com.wonders.xlab.pci;

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

import com.wonders.xlab.common.viewpager.ScrollableViewPager;
import com.wonders.xlab.pci.mvn.model.LoginModel;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.vp_main)
    ScrollableViewPager mVpMain;
    @Bind(R.id.fab_main)
    FloatingActionButton mFab;
    @Bind(R.id.tl_main)
    TabLayout mTabLayout;
    @Bind(R.id.tv_main_my_doctor)
    TextView mTvMyDoctor;

    private LoginModel mLoginModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout.addTab(mTabLayout.newTab().setText("首页"));
        mTabLayout.addTab(mTabLayout.newTab().setText("档案"));

        String tmp = "我的医生";
        SpannableString sp = new SpannableString(tmp);
        //设置斜体
        sp.setSpan(new StyleSpan(Typeface.ITALIC), 0, tmp.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvMyDoctor.setText(sp);

        mLoginModel = new LoginModel();
        mLoginModel.login();
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
}
