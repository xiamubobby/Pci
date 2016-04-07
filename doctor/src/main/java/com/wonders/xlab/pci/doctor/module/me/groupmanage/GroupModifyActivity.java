package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupModifyMemberRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupServiceIconRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupModifyPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupModifyPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupModifyActivity extends AppbarActivity implements GroupModifyPresenter.GroupModifyPresenterListener {
    public final static String EXTRA_GROUP_ID = "groupId";
    private String mGroupId;

    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @Bind(R.id.recycler_view_group_modify_members)
    RecyclerView mRecyclerViewMembers;
    @Bind(R.id.recycler_view_group_modify_service)
    RecyclerView mRecyclerViewService;
    @Bind(R.id.tv_group_modify_group_name)
    TextView mTvGroupName;
    @Bind(R.id.tv_group_modify_group_desc)
    TextView mTvGroupDesc;

    private GroupModifyMemberRVAdapter mMemberRVAdapter;

    private GroupServiceIconRVAdapter mServiceIconRVAdapter;

    private IGroupModifyPresenter mGroupModifyPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.group_modify_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null != intent) {
            mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);
        }

        if (TextUtils.isEmpty(mGroupId)) {
            setToolbarTitle("新建小组");
        }
        mGroupModifyPresenter = new GroupModifyPresenter(this);
        addPresenter(mGroupModifyPresenter);

        mRecyclerViewMembers.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewMembers.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewService.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewService.setItemAnimator(new DefaultItemAnimator());

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGroupModifyPresenter.getGroupInfo(mGroupId);
            }
        });

        setRefreshing(mRefresh, true);
        mGroupModifyPresenter.getGroupInfo(mGroupId);
    }

    @Override
    public void showGroupInfo(GroupModifyBean groupModifyBean) {
        mTvGroupName.setText(groupModifyBean.getGroupName());
        mTvGroupDesc.setText(groupModifyBean.getGroupDesc());

        if (null == mMemberRVAdapter) {
            mMemberRVAdapter = new GroupModifyMemberRVAdapter();
            mRecyclerViewMembers.setAdapter(mMemberRVAdapter);
        }
        mMemberRVAdapter.setDatas(groupModifyBean.getMemberInfoList());

        if (null == mServiceIconRVAdapter) {
            mServiceIconRVAdapter = new GroupServiceIconRVAdapter();
            mRecyclerViewService.setAdapter(mServiceIconRVAdapter);
        }
        mServiceIconRVAdapter.setDatas(groupModifyBean.getPublishedServiceIconList());
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        setRefreshing(mRefresh, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMemberRVAdapter = null;
        mServiceIconRVAdapter = null;
    }
}
