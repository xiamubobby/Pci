package com.wonders.xlab.patient.module.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;

/**
 * Created by jimmy on 16/5/6.
 */
public class OrderListActivity extends AppbarActivity {


    @Override
    public int getContentLayout() {
        return R.layout.order_list_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_activity_my_order);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
