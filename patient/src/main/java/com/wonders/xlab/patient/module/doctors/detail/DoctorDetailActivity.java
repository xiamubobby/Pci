package com.wonders.xlab.patient.module.doctors.detail;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.base.AppbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoctorDetailActivity extends AppbarActivity {
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_GROUP_ID = "group_id";

    private String title;
    private String groupId;

    @Bind(R.id.iv_doctor_detail_portrait)
    ImageView mIvDoctorDetailPortrait;
    @Bind(R.id.recycler_view_doctor_detail_package)
    RecyclerView mRecyclerViewDoctorDetailPackage;
    @Bind(R.id.recycler_view_doctor_detail_member_or_group)
    RecyclerView mRecyclerViewDoctorDetailMemberOrGroup;

    @Override
    public int getContentLayout() {
        return R.layout.doctor_detail_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "医生详情";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        if (null == data) {
            Toast.makeText(this, "获取医生详情失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        title = data.getString(EXTRA_TITLE);
        groupId = data.getString(EXTRA_GROUP_ID);

        setToolbarTitle(title);
    }

}
