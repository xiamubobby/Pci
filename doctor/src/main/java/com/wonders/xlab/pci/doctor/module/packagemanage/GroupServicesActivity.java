package com.wonders.xlab.pci.doctor.module.packagemanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.packagemanage.adapter.GroupServiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.packagemanage.adapter.bean.GroupServiceBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupServicesPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupServicesPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class GroupServicesActivity extends AppbarActivity implements GroupServicesPresenter.GroupServicesPresenterListener {
    private final int REQUEST_CODE_MODIFY = 0;

    public final static String EXTRA_OWNER_ID = "ownerId";
    public final static String EXTRA_IS_ADMIN = "isAdmin";

    private String mOwnerId;
    private boolean mIsAdmin;

    @Bind(R.id.recycler_view_group_service)
    CommonRecyclerView mRecyclerView;

    private GroupServiceRVAdapter mRVAdapter;
    private IGroupServicesPresenter mGroupServicesPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.group_service_list_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null != intent) {
            mOwnerId = intent.getStringExtra(EXTRA_OWNER_ID);
            mIsAdmin = intent.getBooleanExtra(EXTRA_IS_ADMIN,false);
        }

        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this,getResources().getColor(R.color.divider),1));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGroupServicesPresenter.getPackages(mOwnerId);
            }
        });
        mGroupServicesPresenter = new GroupServicesPresenter(this);
        addPresenter(mGroupServicesPresenter);

        mGroupServicesPresenter.getPackages(mOwnerId);
    }

    @Override
    public void showPackages(List<GroupServiceBean> groupServiceBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new GroupServiceRVAdapter();
            mRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent("com.wonders.xlab.pci.doctor.GroupServiceModifyActivity");
                    intent.putExtra(GroupServiceModifyActivity.EXTRA_SERVICE_PACKAGE_ID, mRVAdapter.getBean(position).packageId.get());
                    intent.putExtra(GroupServiceModifyActivity.EXTRA_OWNER_ID, mOwnerId);
                    intent.putExtra(GroupServiceModifyActivity.EXTRA_PUBLISHED, mRVAdapter.getBean(position).published.get());
                    intent.putExtra(GroupServiceModifyActivity.EXTRA_IS_ADMIN, mIsAdmin);
                    startActivityForResult(intent,REQUEST_CODE_MODIFY);
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(groupServiceBeanList);
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
                mGroupServicesPresenter.getPackages(mOwnerId);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mGroupServicesPresenter.getPackages(mOwnerId);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mGroupServicesPresenter.getPackages(mOwnerId);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_MODIFY:
                if (resultCode == GroupServiceModifyActivity.RESULT_CODE_SUCCESS) {
                    mGroupServicesPresenter.getPackages(mOwnerId);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }
}
