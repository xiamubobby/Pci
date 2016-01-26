package com.wonders.xlab.pci.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.activeandroid.query.Delete;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.utils.NotifyUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.SPManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.home.bean.HistoryTaskBean;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.otto.ExitBus;
import com.wonders.xlab.pci.module.setting.SettingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.labelview.LabelView;

public class UserCenterActivity extends AppbarActivity {

    @Bind(R.id.tv_user_center_tel)
    LabelView mTvUserCenterTel;
    @Bind(R.id.tv_user_center_medicare_card)
    LabelView mTvUserCenterMedi;

    @Override
    public int getContentLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public String getToolbarTitle() {
        return "个人中心";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getToolbar().inflateMenu(R.menu.menu_user_center);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_user_center_setting:
                        startActivity(new Intent(UserCenterActivity.this, SettingActivity.class));
                        break;
                }
                return false;
            }
        });

        mTvUserCenterTel.setText(AIManager.getInstance(this).getUserTel());
        mTvUserCenterMedi.setText(AIManager.getInstance(this).getMedicareCard());
    }

    @OnClick(R.id.btn_user_center_exit)
    public void exit() {
        new NotifyUtil().cancelAll(this);
        SPManager.get(this).clear();
        new Delete().from(TodayTaskBean.class).execute();
        new Delete().from(HistoryTaskBean.class).execute();
        OttoManager.post(new ExitBus());
        finish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("个人中心");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("个人中心");
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
