package com.wonders.xlab.pci.doctor.module.me.notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.NotifiGroupInviteRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiGroupInviteBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.INotifiGroupInvitePresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.NotifiGroupInvitePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifiGroupInviteFragment extends BaseFragment implements NotifiGroupInvitePresenter.NotifiGroupInvitePresenterListener {


    @Bind(R.id.recycler_view_group_invite)
    CommonRecyclerView mRecyclerView;

    private NotifiGroupInviteRVAdapter mRVAdapter;

    private INotifiGroupInvitePresenter mInvitePresenter;

    public NotifiGroupInviteFragment() {
        // Required empty public constructor
    }

    public static NotifiGroupInviteFragment getInstance() {
        return new NotifiGroupInviteFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notifi_group_invite_fragment, container, false);
        ButterKnife.bind(this, view);

        mInvitePresenter = new NotifiGroupInvitePresenter(this);
        addPresenter(mInvitePresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(),getResources().getColor(R.color.divider),5));
        mInvitePresenter.getInviteNotifications(AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }

    @Override
    public void showInviteNotifications(List<NotifiGroupInviteBean> inviteBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new NotifiGroupInviteRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(inviteBeanList);
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
