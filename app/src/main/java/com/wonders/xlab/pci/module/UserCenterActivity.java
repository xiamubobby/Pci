package com.wonders.xlab.pci.module;

import android.content.Intent;
import android.os.Bundle;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.SPManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.login.LoginActivity;

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
        mTvUserCenterTel.setText(AIManager.getInstance(this).getUserTel());
        mTvUserCenterMedi.setText(AIManager.getInstance(this).getMedicareCard());
    }

    @OnClick(R.id.btn_user_center_exit)
    public void exit() {
        SPManager.get(this).clear();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
