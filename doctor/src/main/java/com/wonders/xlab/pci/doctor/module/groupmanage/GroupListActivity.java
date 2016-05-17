package com.wonders.xlab.pci.doctor.module.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.Menu;
import android.view.MenuItem;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.groupmanage.adapter.GroupListRVAdapter;
import com.wonders.xlab.pci.doctor.module.groupmanage.adapter.bean.GroupListBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupListPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupListPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class GroupListActivity extends AppbarActivity implements GroupListPresenter.GroupManagePresenterListener {
    private final int REQUEST_CODE_MODIFY = 0;

    @Bind(R.id.recycler_view_group_manage)
    CommonRecyclerView mRecyclerView;

    private IGroupListPresenter mGroupManagePresenter;

    private GroupListRVAdapter mRVAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.group_manage_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 8));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGroupManagePresenter.getGroupList(true, AIManager.getInstance().getDoctorId());
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mGroupManagePresenter.getGroupList(false, AIManager.getInstance().getDoctorId());
            }
        });

        mGroupManagePresenter = new GroupListPresenter(this);
        addPresenter(mGroupManagePresenter);

        mGroupManagePresenter.getGroupList(true, AIManager.getInstance().getDoctorId());
    }

    private void initAdapter() {
        if (null == mRVAdapter) {
            mRVAdapter = new GroupListRVAdapter();
            mRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent("com.wonders.xlab.pci.doctor.GroupModifyActivity");
                    intent.putExtra(GroupModifyActivity.EXTRA_GROUP_ID, mRVAdapter.getBean(position).getGroupId());
                    intent.putExtra(GroupModifyActivity.EXTRA_OWNER_ID, mRVAdapter.getBean(position).getOwnerId());
                    startActivityForResult(intent, REQUEST_CODE_MODIFY);
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }

    @Override
    public void showDoctorGroup(List<GroupListBean> groupListBeanList) {
        mIsCheckingFinish = true;
        initAdapter();
        mRVAdapter.setDatas(groupListBeanList);
    }

    @Override
    public void appendDoctorGroup(List<GroupListBean> groupListBeanList) {
        mIsCheckingFinish = true;
        initAdapter();
        mRVAdapter.appendDatas(groupListBeanList);
    }

    private boolean mIsCheckingFinish = false;
    private boolean mCanCreate = false;

    @Override
    public void cannotCreateMore(boolean canCreate) {
        mCanCreate = canCreate;
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
                mGroupManagePresenter.getGroupList(true, AIManager.getInstance().getDoctorId());
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mGroupManagePresenter.getGroupList(true, AIManager.getInstance().getDoctorId());
            }
        });
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRVAdapter = null;
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mGroupManagePresenter.getGroupList(true, AIManager.getInstance().getDoctorId());
            }
        }, true);
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_MODIFY:
                if (resultCode == GroupModifyActivity.RESULT_CODE_SUCCESS)
                    mGroupManagePresenter.getGroupList(true, AIManager.getInstance().getDoctorId());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                if (!mIsCheckingFinish) {
                    showShortToast("等待校验权限...");
                    break;
                }
                if (mCanCreate) {
                    Intent intent = new Intent("com.wonders.xlab.pci.doctor.GroupModifyActivity");
                    startActivityForResult(intent, REQUEST_CODE_MODIFY);
                } else {
                    showShortToast("您已经创建过小组了！");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
