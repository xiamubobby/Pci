package com.wonders.xlab.pci.module;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyDoctorActivity extends AppbarActivity {

    @Bind(R.id.iv_my_doctor)
    ImageView mIvMyDoctor;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_doctor;
    }

    @Override
    public String getToolbarTitle() {
        return "我的医生";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.pic_my_doctor)
                .crossFade()
                .centerCrop()
                .into(mIvMyDoctor);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的医生");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的医生");
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
