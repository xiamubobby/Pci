package com.wonders.xlab.patient.module.me.setting;

import android.os.Bundle;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.base.AppbarActivity;
import com.wonders.xlab.patient.otto.ForceExitOtto;

import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.btn_setting_exit)
    public void exit() {
        OttoManager.post(new ForceExitOtto());
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
