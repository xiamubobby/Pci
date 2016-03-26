package com.wonders.xlab.pci.module.usercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.AppbarActivity;
import com.wonders.xlab.pci.module.base.mvn.entity.record.userinfo.UserInfoEntity;
import com.wonders.xlab.pci.module.otto.ConnectStateBus;
import com.wonders.xlab.pci.module.setting.SettingActivity;
import com.wonders.xlab.pci.module.usercenter.mvn.model.UserInfoModel;
import com.wonders.xlab.pci.module.usercenter.mvn.view.UserInfoView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.utils.DateUtil;
import rx.subscriptions.CompositeSubscription;

public class UserCenterActivity extends AppbarActivity implements UserInfoView {

    @Bind(R.id.rv_user_info)
    RecyclerView mRvUserInfo;
    @Bind(R.id.refresh_user_info)
    SwipeRefreshLayout mRefresh;

    private UserInfoRVAdapter mUserInfoRVAdapter;

    private UserInfoModel mUserInfoModel;
    private CompositeSubscription mSubscription;

    @Override
    public int getContentLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public String getToolbarTitle() {
        return "个人中心";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        OttoManager.register(this);

        getToolbar().inflateMenu(R.menu.menu_user_center);
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_user_center_setting:
                        startActivity(new Intent(UserCenterActivity.this, SettingActivity.class));
                        break;
                }
                return false;
            }
        });

        mUserInfoModel = new UserInfoModel(this);
        addModel(mUserInfoModel);

        mRvUserInfo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserInfoModel.getUserInfo(AIManager.getInstance(UserCenterActivity.this).getUserId());
            }
        });
        mUserInfoModel.getUserInfo(AIManager.getInstance(this).getUserId());
    }

    @Subscribe
    public void onConnectionChanged(ConnectStateBus bean) {
        if (bean.isConnected()) {
            //断线重连
            if (mUserInfoRVAdapter != null) {
                return;
            }
            mUserInfoModel.getUserInfo(AIManager.getInstance(this).getUserId());
        } else {
            Snackbar.make(mRvUserInfo, getResources().getString(R.string.network_disconnected), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading() {
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                if (mRefresh != null) {
                    mRefresh.setRefreshing(true);
                }
            }
        });
    }

    @Override
    public void hideLoading() {
        if (mRefresh != null) {
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onSuccess(UserInfoEntity.RetValuesEntity entity) {
        if (mUserInfoRVAdapter == null) {
            mUserInfoRVAdapter = new UserInfoRVAdapter(new WeakReference<Context>(this));
            mRvUserInfo.setAdapter(mUserInfoRVAdapter);
        }
        List<UserInfoBean> userInfoBeanList = new ArrayList<>();
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_DIVIDER, "个人历史", ""));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "姓名", entity.getName()));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "性别", entity.getSex()));

        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_DIVIDER, "联系方式", ""));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "医保号", entity.getMedicareCard()));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "身份证号", entity.getIdCardNumber()));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "住址", entity.getAddress()));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "电话", entity.getPhone()));

        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_DIVIDER, "身体体征", ""));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "身高", entity.getHeight() + "cm"));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "起病时间", DateUtil.format(entity.getOnsetTime(), DateUtil.DEFAULT_FORMAT)));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "既往病史", entity.getHistoryCase()));
        userInfoBeanList.add(new UserInfoBean(UserInfoRVAdapter.VIEW_TYPE_ITEM, "吸烟史", entity.getHistorySmoking()));

        mUserInfoRVAdapter.setDatas(userInfoBeanList);
    }

    @Override
    public void onFailed(String message) {

        if (mRefresh != null) {
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        OttoManager.unregister(this);
        if (mUserInfoRVAdapter != null) {
            mUserInfoRVAdapter.clear();
            mUserInfoRVAdapter = null;
        }
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
        ButterKnife.unbind(this);
    }
}
