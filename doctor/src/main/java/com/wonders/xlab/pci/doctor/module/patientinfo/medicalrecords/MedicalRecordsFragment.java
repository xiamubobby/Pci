package com.wonders.xlab.pci.doctor.module.patientinfo.medicalrecords;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.module.patientinfo.medicalrecords.adapter.MedicalRecordsRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientinfo.medicalrecords.adapter.bean.MedicalRecordsBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.MedicalRecordsPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/4/27.
 */
public class MedicalRecordsFragment extends BaseFragment implements MedicalRecordsPresenter.MedicalRecordsPresenterListener {
    private static final String ARG_PATIENT_ID = "patientId";
    private String mPatientId;

    @Bind(R.id.recycler_view_medicalRecords)
    public CommonRecyclerView mRecyclerView;

    MedicalRecordsRVAdapter mRVAdapter;

    MedicalRecordsPresenter mPresenter;

    public MedicalRecordsFragment() {
    }

    public static MedicalRecordsFragment newInstance(String patientId) {
        MedicalRecordsFragment fragment = new MedicalRecordsFragment();
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (null == data) {
            Toast.makeText(getActivity(), "获取就诊记录失败", Toast.LENGTH_SHORT).show();
            return;
        }
        mPatientId = data.getString(ARG_PATIENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medical_records_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new MedicalRecordsPresenter(this);
        addPresenter(mPresenter);
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMedicalRecordsList(mPatientId, true);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMedicalRecordsList(mPatientId, false);
            }
        });
        mPresenter.getMedicalRecordsList(mPatientId, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mRVAdapter = null;

    }

    @Override
    public void showMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList) {
        if (mRVAdapter == null) {
            mRVAdapter = new MedicalRecordsRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(medicalRecordsBeanList);
    }

    @Override
    public void appendMedicalRecordsList(List<MedicalRecordsBean> medicalRecordsBeanList) {
        if (mRVAdapter == null) {
            mRVAdapter = new MedicalRecordsRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.appendDatas(medicalRecordsBeanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                mPresenter.getMedicalRecordsList(mPatientId,true);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mPresenter.getMedicalRecordsList(mPatientId,true);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mPresenter.getMedicalRecordsList(mPatientId,true);
            }
        }, true);
    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
    }
}
