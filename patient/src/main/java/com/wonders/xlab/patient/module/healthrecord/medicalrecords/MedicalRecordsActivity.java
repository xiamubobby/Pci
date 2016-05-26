package com.wonders.xlab.patient.module.healthrecord.medicalrecords;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.base.AppbarActivity;
import com.wonders.xlab.patient.module.healthrecord.medicalrecords.adapter.MedicalRecordsRVAdapter;
import com.wonders.xlab.patient.module.healthrecord.medicalrecords.adapter.bean.MedicalRecordsBean;
import com.wonders.xlab.patient.mvp.presenter.MedicalRecordsPresenterContract;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/5/10.
 */
public class MedicalRecordsActivity extends AppbarActivity implements MedicalRecordsPresenterContract.ViewListener {
    @Bind(R.id.recycler_view_medicalRecords)
    CommonRecyclerView mRecyclerView;

    MedicalRecordsPresenterContract.Actions mMedicalRecordsPresenter;

    MedicalRecordsRVAdapter mRVAdapter;

    private String mPatientId;

    @Override
    public int getContentLayout() {
        return R.layout.medical_records_activity;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_activity_medical_record);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPatientId = AIManager.getInstance().getPatientId();
        mMedicalRecordsPresenter = DaggerMedicalRecordsComponent.builder()
                .applicationComponent(((XApplication) getApplication()).getComponent())
                .medicalRecordsModule(new MedicalRecordsModule(this))
                .build()
                .getMedicalRecordsPresenter();
        addPresenter(mMedicalRecordsPresenter);
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mMedicalRecordsPresenter.getMedicalRecordsList(mPatientId, false);

            }
        });
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMedicalRecordsPresenter.getMedicalRecordsList(mPatientId, true);

            }
        });
        mMedicalRecordsPresenter.getMedicalRecordsList(mPatientId, true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView = null;
    }

    private void ininAdapter() {
        if (mRVAdapter == null) {
            mRVAdapter = new MedicalRecordsRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }

    @Override
    public void showMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList) {
        ininAdapter();
        mRVAdapter.setDatas(medicalRecordsBeanList);
    }

    @Override
    public void appendMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList) {
        ininAdapter();
        mRVAdapter.appendDatas(medicalRecordsBeanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        showShortToast(message);
    }

    @Override
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.showNetworkErrorView(new CommonRecyclerView.OnNetworkErrorViewClickListener() {
            @Override
            public void onClick() {
                mMedicalRecordsPresenter.getMedicalRecordsList(mPatientId, true);
            }
        }, R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mMedicalRecordsPresenter.getMedicalRecordsList(mPatientId, true);
            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mMedicalRecordsPresenter.getMedicalRecordsList(mPatientId, true);
            }
        }, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showToast(String message) {
        showToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }
}
