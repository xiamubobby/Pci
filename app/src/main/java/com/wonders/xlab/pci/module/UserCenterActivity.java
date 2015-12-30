package com.wonders.xlab.pci.module;

import android.os.Bundle;

import com.activeandroid.query.Delete;
import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.common.utils.NotifyUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.application.SPManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.home.bean.HistoryTaskBean;
import com.wonders.xlab.pci.module.home.bean.TodayTaskBean;
import com.wonders.xlab.pci.module.rxbus.ExitBus;

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
        new NotifyUtil().cancelAll(this);
        SPManager.get(this).clear();
        new Delete().from(TodayTaskBean.class).execute();
        new Delete().from(HistoryTaskBean.class).execute();
        OttoManager.post(new ExitBus());
        finish();
    }
}
