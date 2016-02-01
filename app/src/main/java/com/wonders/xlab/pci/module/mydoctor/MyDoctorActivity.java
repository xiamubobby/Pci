package com.wonders.xlab.pci.module.mydoctor;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.mydoctor.mvn.DoctorInfoEntity;
import com.wonders.xlab.pci.module.mydoctor.mvn.MyDoctorModel;
import com.wonders.xlab.pci.module.mydoctor.mvn.MyDoctorView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyDoctorActivity extends AppbarActivity implements MyDoctorView {

    @Bind(R.id.rv_my_doctor)
    RecyclerView mRvMyDoctor;
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    private MyDoctorRVAdapter mDoctorRVAdapter;
    private MyDoctorModel mMyDoctorModel;

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

        mMyDoctorModel = new MyDoctorModel(this);
        addModel(mMyDoctorModel);

        mRvMyDoctor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvMyDoctor.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider)));

        mMyDoctorModel.getDoctorList(AIManager.getInstance(this).getUserId(), AIManager.getInstance(this).getUserTel());
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDoctorList(List<DoctorInfoEntity> doctorInfoList) {
        if (mDoctorRVAdapter == null) {
            mDoctorRVAdapter = new MyDoctorRVAdapter(new WeakReference<Context>(this));
            mRvMyDoctor.setAdapter(mDoctorRVAdapter);
        }
        mDoctorRVAdapter.setDatas(doctorInfoList);
    }

    @Override
    public void showError(String message) {
        showSnackbar(mContentView, message, true);
    }
}
