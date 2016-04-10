package com.wonders.xlab.pci.doctor.module.me.groupmanage.servicemanage;

import android.content.Intent;
import android.os.Bundle;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.servicemanage.adapter.GroupServiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.servicemanage.adapter.bean.GroupServiceBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupServicesPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupServicesPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class GroupServicesActivity extends AppbarActivity implements GroupServicesPresenter.GroupServicesPresenterListener {
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
        mGroupServicesPresenter = new GroupServicesPresenter(this);
        addPresenter(mGroupServicesPresenter);

        mGroupServicesPresenter.getPackages(mGroupId);
    }

    @Override
    public void showPackages(List<GroupServiceBean> groupServiceBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new GroupServiceRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(groupServiceBeanList);
    }

    @Override
    public void showError(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mRVAdapter = null;
    }
}
