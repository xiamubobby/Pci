package com.wonders.xlab.pci.doctor.module.me.notification;


import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.NotifiOthersRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.notification.adapter.bean.NotifiOthersBean;
import com.wonders.xlab.pci.doctor.module.me.notification.presenter.impl.NotifiOthersPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * 其他通知
 */
public class NotifiOthersFragment extends BaseFragment implements NotifiOthersPresenter.NotifiOthersPresenterListener {

    @Bind(R.id.recycler_view_notifi_others)
    CommonRecyclerView mRecyclerView;

    @Bind(R.id.card_notifi_others_bottom)
    CardView mCardViewBottom;

    private NotifiOthersRVAdapter mRVAdapter;

    private NotifiOthersPresenter mOthersPresenter;

    public NotifiOthersFragment() {
        // Required empty public constructor
    }

    public static NotifiOthersFragment getInstance() {
        return new NotifiOthersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notifi_others_fragment, container, false);
        ButterKnife.bind(this, view);
        mOthersPresenter = new NotifiOthersPresenter(this);
        addPresenter(mOthersPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        registerForContextMenu(mRecyclerView);

        mOthersPresenter.getOthersNotifi(AIManager.getInstance().getDoctorId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "多选");
        menu.add(0, 1, 1, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                break;
            case 1:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void showOthersNotifications(List<NotifiOthersBean> notifications) {
        if (null == mRVAdapter) {
            mRVAdapter = new NotifiOthersRVAdapter();
            mRVAdapter.setOnLongClickListener(new SimpleRVAdapter.OnLongClickListener() {
                @Override
                public void onItemLongClick(int position) {
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(notifications);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

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

            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {

            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {

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
