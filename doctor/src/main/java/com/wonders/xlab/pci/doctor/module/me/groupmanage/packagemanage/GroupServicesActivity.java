package com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.adapter.GroupServiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.adapter.bean.GroupServiceBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupServicesPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupServicesPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class GroupServicesActivity extends AppbarActivity implements GroupServicesPresenter.GroupServicesPresenterListener {
    private final int REQUEST_CODE_MODIFY = 0;

    public final static String EXTRA_GROUP_ID = "groupId";
    private String mGroupId;

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
            mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);
        }

        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this,getResources().getColor(R.color.divider),1));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGroupServicesPresenter.getPackages(mGroupId);
            }
        });
        mGroupServicesPresenter = new GroupServicesPresenter(this);
        addPresenter(mGroupServicesPresenter);

        mGroupServicesPresenter.getPackages(mGroupId);
    }

    @Override
    public void showPackages(List<GroupServiceBean> groupServiceBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new GroupServiceRVAdapter();
            mRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent("com.wonders.xlab.pci.doctor.GroupServiceModifyActivity");
                    intent.putExtra(GroupServiceModifyActivity.EXTRA_PACKAGE_ID, mRVAdapter.getBean(position).packageId.get());
                    intent.putExtra(GroupServiceModifyActivity.EXTRA_GROUP_ID, mGroupId);
                    intent.putExtra(GroupServiceModifyActivity.EXTRA_PUBLISHED, mRVAdapter.getBean(position).published.get());
                    startActivityForResult(intent,REQUEST_CODE_MODIFY);
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(groupServiceBeanList);
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
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
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_MODIFY:
                if (resultCode == GroupServiceModifyActivity.RESULT_CODE_SUCCESS) {
                    mGroupServicesPresenter.getPackages(mGroupId);
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
