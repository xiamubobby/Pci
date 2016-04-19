package com.wonders.xlab.pci.doctor.module.me.notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.NotifiGroupInviteRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiGroupInviteBean;
import com.wonders.xlab.pci.doctor.module.me.notification.otto.NotifiGroupInviteOtto;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.INotifiGroupInvitePresenter;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.impl.NotifiGroupInvitePresenter;

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
        OttoManager.register(this);

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
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 5));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mInvitePresenter.getInviteNotifications(AIManager.getInstance().getDoctorId());
            }
        });
        mInvitePresenter.getInviteNotifications(AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }

    @Subscribe
    public void refresh(NotifiGroupInviteOtto othersOtto) {
        mInvitePresenter.getInviteNotifications(AIManager.getInstance().getDoctorId());
    }

    @Override
    public void showInviteNotifications(List<NotifiGroupInviteBean> inviteBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new NotifiGroupInviteRVAdapter();
            mRVAdapter.setOnOperationClickListener(new NotifiGroupInviteRVAdapter.OnOperationClickListener() {
                @Override
                public void onAgreeClick(int position) {
                    mInvitePresenter.agreeOrRejectJoinDoctorGroup(AIManager.getInstance().getDoctorId(),mRVAdapter.getBean(position).getOwnerId(), true);
                }

                @Override
                public void onRefuseClick(int position) {
                    mInvitePresenter.agreeOrRejectJoinDoctorGroup(AIManager.getInstance().getDoctorId(), mRVAdapter.getBean(position).getOwnerId(), false);
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(inviteBeanList);
    }

    @Override
    public void onAgreeSuccess(String ownerId, String message) {
        showShortToast(message);
        mInvitePresenter.getInviteNotifications(AIManager.getInstance().getDoctorId());
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
                mInvitePresenter.getInviteNotifications(AIManager.getInstance().getDoctorId());
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mInvitePresenter.getInviteNotifications(AIManager.getInstance().getDoctorId());
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mInvitePresenter.getInviteNotifications(AIManager.getInstance().getDoctorId());
            }
        });
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

}
