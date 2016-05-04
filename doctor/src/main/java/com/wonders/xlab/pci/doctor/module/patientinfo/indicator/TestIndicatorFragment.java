package com.wonders.xlab.pci.doctor.module.patientinfo.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.patientinfo.indicator.adapter.TestIndicatorRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientinfo.indicator.adapter.bean.TestIndicatorBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.TestIndicatorPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * Created by jimmy on 16/4/27.
 */
public class TestIndicatorFragment extends BaseFragment implements TestIndicatorPresenter.TestIndicatorPresenterListener {


    @Bind(R.id.recycler_view_prescription)
    CommonRecyclerView mRecyclerView;

    private TestIndicatorRVAdapter mRVAdapter;

    private TestIndicatorPresenter mPresenter;

    public static TestIndicatorFragment newInstance(String patientId) {
        TestIndicatorFragment fragment = new TestIndicatorFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_indicator_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new TestIndicatorPresenter(this);
        addPresenter(mPresenter);
        mPresenter.getTestIndicatorList("", AIManager.getInstance().getDoctorId());

    }

    @Override
    public void showTestIndicatorList(List<TestIndicatorBean> testIndicatorBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new TestIndicatorRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(testIndicatorBeanList);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRVAdapter = null;
        ButterKnife.unbind(this);
    }
}
