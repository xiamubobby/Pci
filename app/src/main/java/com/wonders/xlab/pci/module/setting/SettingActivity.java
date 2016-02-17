package com.wonders.xlab.pci.module.setting;

import android.os.Bundle;

import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.utils.NotifyUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.SPManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.otto.ExitBus;

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
        return getString(R.string.label_setting);
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
        new NotifyUtil().cancelAll(this);
        SPManager.get(this).clear();
        OttoManager.post(new ExitBus());
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
