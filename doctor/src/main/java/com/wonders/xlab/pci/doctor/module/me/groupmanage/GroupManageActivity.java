package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupListRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupListBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupListPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupListPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class GroupManageActivity extends AppbarActivity implements GroupListPresenter.GroupManagePresenterListener {

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
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this,getResources().getColor(R.color.divider),8));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGroupManagePresenter.getGroupList(true, AIManager.getInstance(GroupManageActivity.this).getUserId());
            }
        });
        mRecyclerView.setOnLoadMoreListener(new CommonRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });

        mGroupManagePresenter = new GroupListPresenter(this);
        addPresenter(mGroupManagePresenter);

        mGroupManagePresenter.getGroupList(true, AIManager.getInstance(this).getUserId());
    }

    @Override
    public void showDoctorGroup(List<GroupListBean> groupListBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new GroupListRVAdapter();
            mRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent("com.wonders.xlab.pci.doctor.GroupModifyActivity");
                    intent.putExtra(GroupModifyActivity.EXTRA_GROUP_ID, mRVAdapter.getBean(position).getGroupId());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(groupListBeanList);
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
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
    public void showEmptyView() {

    }
}
