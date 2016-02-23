package com.wonders.xlab.pci.doctor.module.userinfo;

import android.os.Bundle;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.pullloadmore.PullLoadMoreRecyclerView;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.userinfo.adapter.UserInfoRVAdapter;
import com.wonders.xlab.pci.doctor.module.userinfo.bean.UserInfoBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.UserInfoPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IUserInfoPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class UserInfoActivity extends AppbarActivity implements IUserInfoPresenter {

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
        ButterKnife.bind(this);

        mRecyclerView.setLinearLayout();
        mRecyclerView.getRecyclerView().addItemDecoration(new VerticalItemDecoration(this,getResources().getColor(R.color.divider),1));
        mRecyclerView.setPushRefreshEnable(false);//disable load more

        mUserInfoPresenter = new UserInfoPresenter(this);
        addPresenter(mUserInfoPresenter);

        mUserInfoPresenter.getUserInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void showUserInfo(List<UserInfoBean> userInfoBeanList) {
        if (mUserInfoRVAdapter == null) {
            mUserInfoRVAdapter = new UserInfoRVAdapter();
        }
        mUserInfoRVAdapter.setDatas(userInfoBeanList);
        mRecyclerView.setAdapter(mUserInfoRVAdapter);

    }

    @Override
    public void showError(String message) {

    }
}
