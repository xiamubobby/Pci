package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupDoctorMultiChoiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupAuthPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupAuthPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class GroupAuthActivity extends AppbarActivity implements GroupAuthPresenter.GroupAuthPresenterListener {
    public final static int RESULT_CODE_SUCCESS = 0;
    public final static String EXTRA_GROUP_ID = "groupId";
    private String mGroupId;

    @Bind(R.id.recycler_view_group_auth)
    CommonRecyclerView mRecyclerView;

    private GroupDoctorMultiChoiceRVAdapter mRVAdapter;

    private IGroupAuthPresenter mGroupAuthPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.group_auth_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (null == intent) {
            showShortToast("获取成员失败，请重试！");
            finish();
            return;
        }
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);
        if (TextUtils.isEmpty(mGroupId)) {
            showShortToast("获取成员失败，请重试！");
            finish();
            return;
        }

        mGroupAuthPresenter = new GroupAuthPresenter(this);
        addPresenter(mGroupAuthPresenter);

        mGroupAuthPresenter.getGroupMemberList(mGroupId);
    }

    @Override
    public void authorizeSuccess(String message) {
        showShortToast(message);
        setResult(RESULT_CODE_SUCCESS);
        finish();
    }

    @Override
    public void showMemberList(List<GroupDoctorBean> doctorBeanList) {
        if (null == mRVAdapter) {
            mRVAdapter = new GroupDoctorMultiChoiceRVAdapter();
            mRecyclerView.setAdapter(mRVAdapter);
        }
        mRVAdapter.setDatas(doctorBeanList);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_confirm:
                mGroupAuthPresenter.authorize(mRVAdapter.getBeanList());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRVAdapter = null;
    }
}
