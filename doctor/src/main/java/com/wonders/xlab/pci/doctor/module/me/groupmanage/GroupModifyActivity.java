package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupModifyMemberRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupSimpleIconRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyMemberBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupModifyPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupModifyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class GroupModifyActivity extends AppbarActivity implements GroupModifyPresenter.GroupModifyPresenterListener {
    private final int REQUEST_CODE_NAME = 0;
    private final int REQUEST_CODE_DESC = 1;
    private final int REQUEST_CODE_INVITE_DOCTOR = 2;

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

    @Bind(R.id.tv_group_modify_group_desc_trigger)
    TextView mTvGroupDescTrigger;
    @Bind(R.id.ll_group_modify_service)
    LinearLayout mLlService;
    @Bind(R.id.tv_group_modify_auth)
    TextView mTvAuth;
    @Bind(R.id.btn_group_modify_dismiss)
    Button mBtnDismiss;

    private GroupModifyMemberRVAdapter mMemberRVAdapter;

    private GroupSimpleIconRVAdapter mServiceIconRVAdapter;

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

        RxView.clicks(mTvGroupDescTrigger)
                .throttleFirst(Constant.VIEW_CLICK_SKIP_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent nameIntent = new Intent("com.wonders.xlab.pci.doctor.GroupDescModifyActivity");
                        nameIntent.putExtra(GroupNameModifyActivity.EXTRA_HINT, "小组简介");
                        nameIntent.putExtra(GroupNameModifyActivity.EXTRA_TEXT, mTvGroupDesc.getText().toString());
                        startActivityForResult(nameIntent, REQUEST_CODE_DESC);
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
            mMemberRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    switch (mMemberRVAdapter.getBean(position).getType()) {
                        case GroupModifyMemberBean.TYPE_ADD:
                            startActivityForResult(new Intent("com.wonders.xlab.pci.doctor.GroupInviteActivity"),REQUEST_CODE_INVITE_DOCTOR);
                            break;
                    }
                }
            });
            mRecyclerViewMembers.setAdapter(mMemberRVAdapter);
        }
        mMemberRVAdapter.setDatas(groupModifyBean.getMemberInfoList());

        if (null == mServiceIconRVAdapter) {
            mServiceIconRVAdapter = new GroupSimpleIconRVAdapter();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_NAME:
                if (resultCode == GroupNameModifyActivity.RESULT_CODE_SUCCESS) {
                    String name = data.getStringExtra(GroupNameModifyActivity.EXTRA_RESULT);
                    if (!TextUtils.isEmpty(name)) {
                        mTvGroupName.setText(name);
                    }
                }
                break;
            case REQUEST_CODE_DESC:
                if (resultCode == GroupDescModifyActivity.RESULT_CODE_SUCCESS) {
                    String name = data.getStringExtra(GroupNameModifyActivity.EXTRA_RESULT);
                    if (!TextUtils.isEmpty(name)) {
                        mTvGroupDesc.setText(name);
                    }
                }
                break;
            case REQUEST_CODE_INVITE_DOCTOR:

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMemberRVAdapter = null;
        mServiceIconRVAdapter = null;
    }
}
