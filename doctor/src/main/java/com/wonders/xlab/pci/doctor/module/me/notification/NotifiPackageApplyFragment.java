package com.wonders.xlab.pci.doctor.module.me.notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.NotifiPackageApplyRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiPackageApplyBean;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.INotifiPackageApplyPresenter;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.impl.NotifiPackageApplyPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifiPackageApplyFragment extends BaseFragment implements NotifiPackageApplyPresenter.NotifiPackageApplyPresenterListener {

    @Bind(R.id.recycler_view_package_apply)
    CommonRecyclerView mRecyclerView;

    private NotifiPackageApplyRVAdapter mRVAdapter;

    private INotifiPackageApplyPresenter mPresenter;

    public NotifiPackageApplyFragment() {
        // Required empty public constructor
    }

    public static NotifiPackageApplyFragment getInstance() {
        return new NotifiPackageApplyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notifi_package_apply_fragment, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new NotifiPackageApplyPresenter(this);
        addPresenter(mPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(),getResources().getColor(R.color.divider),5));
        mPresenter.getPackageApplyNotifications(AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }

    @Override
    public void showPackageApplyList(List<NotifiPackageApplyBean> applyBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new NotifiPackageApplyRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(applyBeanList);
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
