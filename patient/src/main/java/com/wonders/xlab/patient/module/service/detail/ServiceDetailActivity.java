package com.wonders.xlab.patient.module.service.detail;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by WZH on 16/5/9.
 */
public class ServiceDetailActivity extends AppbarActivity {

    @Bind(R.id.view_pager_service_detail)
    ViewPager mViewPager;
    @Override
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

}
