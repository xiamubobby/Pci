package com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.adapter.SurgicalHistoryRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientinfo.surgicalhistory.adapter.bean.SurgicalHistoryBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.SurgicalHistoryPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/4/27.
 * 住院手术室
 */
public class SurgicalHistoryFragment extends BaseFragment implements SurgicalHistoryPresenter.SurgicalHistoryPresenterListener {


    @Bind(R.id.recycler_view_surgicalHistory)
    public CommonRecyclerView mRecyclerView;

    private SurgicalHistoryRVAdapter mRVAdapter;

    private SurgicalHistoryPresenter mPresenter;

    public static SurgicalHistoryFragment newInstance(String patientId) {
        SurgicalHistoryFragment fragment = new SurgicalHistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.surgical_history_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new SurgicalHistoryPresenter(this);
        addPresenter(mPresenter);
        mPresenter.getSurgicalHistoryList("", AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mRVAdapter = null;

    }

    @Override
    public void showSurgicalHistoryList(List<SurgicalHistoryBean> surgicalHistoryBeanList) {
        if (mRVAdapter == null) {
            mRVAdapter = new SurgicalHistoryRVAdapter(getActivity());
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(surgicalHistoryBeanList);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {

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
