package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupDoctorMultiChoiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupRemoveDoctorPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupRemoveDoctorPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;

public class GroupRemoveDoctorActivity extends AppbarActivity implements GroupRemoveDoctorPresenter.GroupRemoveDoctorPresenterListener {
    public final static int RESULT_CODE_SUCCESS = 0;

    public final static String EXTRA_GROUP_ID = "groupId";

    private String mGroupId;

    @Bind(R.id.recycler_view_group_remove_doctor)
    CommonRecyclerView mRecyclerView;

    private GroupDoctorMultiChoiceRVAdapter mMultiChoiceRVAdapter;

    private IGroupRemoveDoctorPresenter mRemoveDoctorPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.group_remove_doctor_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null == intent) {
            showShortToast("获取小组成员失败，请重试！");
            finish();
            return;
        }
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);
        if (TextUtils.isEmpty(mGroupId)) {
            showShortToast("获取小组成员失败，请重试！");
            finish();
            return;
        }

        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this,getResources().getColor(R.color.divider),1));
        mRemoveDoctorPresenter = new GroupRemoveDoctorPresenter(this);
        addPresenter(mRemoveDoctorPresenter);

        mRemoveDoctorPresenter.getCurrentMemberList(mGroupId);
    }

    @Override
    public void showMemberList(List<GroupDoctorBean> doctorBeanList) {
        if (null == mMultiChoiceRVAdapter) {
            mMultiChoiceRVAdapter = new GroupDoctorMultiChoiceRVAdapter();
            mRecyclerView.setAdapter(mMultiChoiceRVAdapter);
        }
        mMultiChoiceRVAdapter.setDatas(doctorBeanList);
    }

    @Override
    public void removeSuccess() {
        dismissProgressDialog();
        setResult(RESULT_CODE_SUCCESS);
        finish();
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showEmptyView() {
        mRecyclerView.showEmptyView();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_remove,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_remove:
                showProgressDialog("","正在移除，请稍候...");
                mRemoveDoctorPresenter.removeMembers(mMultiChoiceRVAdapter.getBeanList());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMultiChoiceRVAdapter = null;
    }
}
