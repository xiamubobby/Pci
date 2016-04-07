package com.wonders.xlab.pci.doctor.module.chatroom.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.chatroom.userinfo.adapter.UserInfoRVAdapter;
import com.wonders.xlab.pci.doctor.module.chatroom.userinfo.bean.UserInfoBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.UserInfoPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class UserInfoActivity extends AppbarActivity implements UserInfoPresenter.UserInfoPresenterListener {
    public final static String EXTRA_PATIENT_ID = "patientId";

    private String mPatientId;

    @Bind(R.id.recycler_view_user_info)
    PullLoadMoreRecyclerView mRecyclerView;

    private UserInfoRVAdapter mUserInfoRVAdapter;

    private UserInfoPresenter mUserInfoPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.user_info_activity;
    }

    @Override
    public String getToolbarTitle() {
        return "基本信息";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (null == intent) {
            Toast.makeText(this, "获取患者信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mPatientId = intent.getStringExtra(EXTRA_PATIENT_ID);
        if (TextUtils.isEmpty(mPatientId)) {
            Toast.makeText(this, "获取患者信息失败，请重试！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ButterKnife.bind(this);

        mRecyclerView.setLinearLayout(false);
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerView.setPushRefreshEnable(false);//disable load more
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mUserInfoPresenter.getUserInfo(mPatientId);
            }

            @Override
            public void onLoadMore() {

            }
        });

        mUserInfoPresenter = new UserInfoPresenter(this);
        addPresenter(mUserInfoPresenter);

        mRecyclerView.setRefreshing(true);
        mUserInfoPresenter.getUserInfo(mPatientId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showUserInfo(List<UserInfoBean> userInfoBeanList) {
        mRecyclerView.setPullLoadMoreCompleted();
        if (mUserInfoRVAdapter == null) {
            mUserInfoRVAdapter = new UserInfoRVAdapter();
        }
        mUserInfoRVAdapter.setDatas(userInfoBeanList);
        mRecyclerView.setAdapter(mUserInfoRVAdapter);

    }

    @Override
    public void showError(String message) {
        mRecyclerView.setPullLoadMoreCompleted();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {

    }
}
