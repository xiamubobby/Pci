package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.adapter.MedicalRecordRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.MedicalRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPagerActivity;

public class MedicalRecordActivity extends AppbarActivity implements MedicalRecordPresenter.MedicalRecordPresenterListener {

    public static final String EXTRA_PATIENT_ID = "patientId";

    private static final int REQUEST_CODE = 1123;

    @Bind(R.id.recycler_view_medical_record)
    PullLoadMoreRecyclerView mRecyclerView;
    private MedicalRecordRVAdapter mMedicalRecordRVAdapter;

    private MedicalRecordPresenter mMedicalRecordPresenter;

    private String mPatientId;

    @Override
    public int getContentLayout() {
        return R.layout.medical_record_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "就诊记录";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null == intent) {
            Toast.makeText(this, "获取患者就诊记录失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mPatientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        if (TextUtils.isEmpty(mPatientId)) {
            Toast.makeText(this, "获取患者就诊记录失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mRecyclerView.setLinearLayout(false);
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 5));
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mMedicalRecordPresenter.getMedicalRecordList(mPatientId);
            }

            @Override
            public void onLoadMore() {

            }
        });

        mMedicalRecordPresenter = new MedicalRecordPresenter(this);
        addPresenter(mMedicalRecordPresenter);

        mRecyclerView.setRefreshing(true);
        mMedicalRecordPresenter.getMedicalRecordList(mPatientId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showMedicalRecordList(List<MedicalRecordBean> beanList) {
        mRecyclerView.setPullLoadMoreCompleted();
        if (null == mMedicalRecordRVAdapter) {
            mMedicalRecordRVAdapter = new MedicalRecordRVAdapter();
            mMedicalRecordRVAdapter.setOnPhotoClickListener(new MedicalRecordRVAdapter.OnPhotoClickListener() {
                @Override
                public void onPhotoClick(ArrayList<String> photoUrls, int selectedPosition) {
                    Intent intent = new Intent(MedicalRecordActivity.this, PhotoPagerActivity.class);
                    intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, selectedPosition);
                    intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photoUrls);
                    intent.putExtra(PhotoPagerActivity.EXTRA_SHOW_DELETE, false); // default is true
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }
        mMedicalRecordRVAdapter.setDatas(beanList);

        mRecyclerView.setAdapter(mMedicalRecordRVAdapter);
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.setPullLoadMoreCompleted();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void hideLoading() {

    }
}
