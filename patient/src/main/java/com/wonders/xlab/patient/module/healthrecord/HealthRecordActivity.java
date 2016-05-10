package com.wonders.xlab.patient.module.healthrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.auth.authorize.AuthorizeActivity;
import com.wonders.xlab.patient.module.healthrecord.healthrecords.HealthRecordsActivity;
import com.wonders.xlab.patient.module.healthrecord.prescription.PrescriptionActivity;
import com.wonders.xlab.patient.module.healthrecord.surgicalhistory.SurgicalHistoryActivity;
import com.wonders.xlab.patient.module.healthrecord.testindicator.TestIndicatorActivity;
import com.wonders.xlab.patient.module.home.adapter.HomeRVAdapter;
import com.wonders.xlab.patient.module.home.adapter.bean.HomeItemBean;
import com.wonders.xlab.patient.module.medicalpicture.MedicalPictureActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HealthRecordActivity extends AppbarActivity {

    @Bind(R.id.recycler_view_health_record)
    RecyclerView mRecyclerView;
    @Bind(R.id.btn_health_record_authorize)
    Button mBtnAuthorize;

    private HomeRVAdapter homeRVAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.health_record_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setupBottomFunctionView();
    }

    @OnClick(R.id.btn_health_record_authorize)
    public void authorize() {
        Intent intent = new Intent(HealthRecordActivity.this, AuthorizeActivity.class);
        startActivity(intent);
    }

    private void setupBottomFunctionView() {
        if (null == homeRVAdapter) {
            homeRVAdapter = new HomeRVAdapter();
            homeRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(HealthRecordActivity.this, TestIndicatorActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(HealthRecordActivity.this, HealthRecordsActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(HealthRecordActivity.this, PrescriptionActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(HealthRecordActivity.this, SurgicalHistoryActivity.class));
                            break;
                        case 4:
                            startActivity(new Intent(HealthRecordActivity.this, MedicalPictureActivity.class));
                            break;

                    }
                }
            });
            ArrayList<HomeItemBean> beanArrayList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                HomeItemBean homeItemBean = new HomeItemBean();
                int drawableResId = R.drawable.ic_home_daily_record;
                int backgroundDrawableId = R.drawable.shape_home_label_daily_record;
                String title = null;

                switch (i) {
                    case 0:
                        backgroundDrawableId = R.drawable.shape_home_label_daily_record;
                        drawableResId = R.drawable.ic_home_daily_record;
                        title = "检验指标";
                        break;
                    case 1:
                        backgroundDrawableId = R.drawable.shape_home_label_medical_report;
                        drawableResId = R.drawable.ic_home_medical_record;
                        title = "就诊记录";
                        break;
                    case 2:
                        backgroundDrawableId = R.drawable.shape_home_label_medicine_remind;
                        drawableResId = R.drawable.ic_home_medicine_remind;
                        title = "住院手术史";
                        break;
                    case 3:
                        backgroundDrawableId = R.drawable.shape_home_label_health_report;
                        drawableResId = R.drawable.ic_home_health_report;
                        title = "处方清单";
                        break;
                    case 4:
                        backgroundDrawableId = R.drawable.shape_home_label_medical_report;
                        drawableResId = R.drawable.ic_home_medical_record;
                        title = "补充资料";
                        break;
                }
                homeItemBean.setBackgroundDrawableId(backgroundDrawableId);
                homeItemBean.setDrawableResId(drawableResId);
                homeItemBean.setTitle(title);

                beanArrayList.add(homeItemBean);
            }
            homeRVAdapter.setDatas(beanArrayList);
        }
        mRecyclerView.setAdapter(homeRVAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
