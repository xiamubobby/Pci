package com.wonders.xlab.patient.module.me.setting;

import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

import butterknife.ButterKnife;

/**
 * Created by hua on 16/1/26.
 */
public class SettingActivity extends AppbarActivity {

    @Override
    public int getContentLayout() {
        return R.layout.setting_layout;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_activity_setting);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        getFragmentManager().beginTransaction()
                .add(R.id.setting_container, SettingFragment.newInstance())
                .commitAllowingStateLoss();
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
