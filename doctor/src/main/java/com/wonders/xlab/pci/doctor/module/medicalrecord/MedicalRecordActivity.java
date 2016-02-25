package com.wonders.xlab.pci.doctor.module.medicalrecord;

import android.content.Intent;
import android.os.Bundle;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.medicalrecord.adapter.MedicalRecordRVAdapter;
import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.MedicalRecordPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IMedicalRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPagerActivity;

public class MedicalRecordActivity extends AppbarActivity implements IMedicalRecordPresenter {

    private static final int REQUEST_CODE = 1123;

    @Bind(R.id.recycler_view_medical_record)
    PullLoadMoreRecyclerView mRecyclerView;
    private MedicalRecordRVAdapter mMedicalRecordRVAdapter;

    private MedicalRecordPresenter mMedicalRecordPresenter;

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

        mRecyclerView.setLinearLayout();
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 5));

        mMedicalRecordPresenter = new MedicalRecordPresenter(this);
        addPresenter(mMedicalRecordPresenter);

        mMedicalRecordPresenter.getMedicalRecordList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showMedicalRecordList(List<MedicalRecordBean> beanList) {

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
    public void showError(String message) {

    }
}
