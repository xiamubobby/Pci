package com.wonders.xlab.patient.module.service.detail;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.mvp.entity.ServiceDetailEntity;
import com.wonders.xlab.patient.mvp.presenter.ServiceDetailPresenterContract;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailActivity extends AppbarActivity implements ServiceDetailPresenterContract.ViewListener {

    @Bind(R.id.view_pager_service_detail)
    ViewPager mViewPager;

    private ServiceDetailPresenterContract.Actions mServiceDetailPresenter;

    public int getContentLayout() {
        return R.layout.service_detail_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "服务详情／购买";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;//model对接时数量可能有变动
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
            }

        });
    }

    @Override
    public void showServiceDetail(ServiceDetailEntity entity) {

    }

    @Override
    public void showLoading(String message) {
        showProgressDialog("", message, null);
    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {

    }


}
