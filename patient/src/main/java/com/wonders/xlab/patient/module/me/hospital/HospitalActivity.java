package com.wonders.xlab.patient.module.me.hospital;

import android.content.Intent;
import android.os.Bundle;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.me.hospital.adapter.HospitalBean;
import com.wonders.xlab.patient.module.me.hospital.adapter.HospitalRVAdapter;
import com.wonders.xlab.patient.module.me.hospital.di.DaggerHospitalComponent;
import com.wonders.xlab.patient.module.me.hospital.di.HospitalModule;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class HospitalActivity extends AppbarActivity implements HospitalContract.ViewListener {

    public static final String EXTRA_RESULT_ID = "hospitalId";
    public static final String EXTRA_RESULT_NAME = "hospitalName";

    @Bind(R.id.recyclerView)
    CommonRecyclerView mRecyclerView;

    private HospitalRVAdapter mRVAdapter;
    private HospitalContract.Presenter mHospitalPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.hospital_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mHospitalPresenter = DaggerHospitalComponent.builder()
                .applicationComponent(XApplication.getComponent())
                .hospitalModule(new HospitalModule(this))
                .build()
                .getHospitalPresenter();
        addPresenter(mHospitalPresenter);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mHospitalPresenter.getAllHospitals();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showHospitalList(List<HospitalBean> hospitalBeanList) {
        if (mRVAdapter == null) {
            mRVAdapter = new HospitalRVAdapter();
            mRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    HospitalBean bean = mRVAdapter.getBean(position);
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_RESULT_ID, bean.getId());
                    intent.putExtra(EXTRA_RESULT_NAME, bean.getName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(hospitalBeanList);
    }

    @Override
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.showNetworkErrorView(null, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(null, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(null, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }
}