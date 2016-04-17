package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupDoctorMultiChoiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupAuthorizeBody;
import com.wonders.xlab.pci.doctor.data.presenter.IGroupAuthPresenter;
import com.wonders.xlab.pci.doctor.data.presenter.impl.GroupAuthPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class GroupAuthActivity extends AppbarActivity implements GroupAuthPresenter.GroupAuthPresenterListener {
    public final static int RESULT_CODE_SUCCESS = 12345;
    public final static String EXTRA_GROUP_ID = "groupId";
    private String mGroupId;
    private String mDoctorId;

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
        mDoctorId = AIManager.getInstance().getDoctorId();

        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGroupAuthPresenter.getGroupMemberList(mDoctorId, mGroupId);
            }
        });
        mGroupAuthPresenter = new GroupAuthPresenter(this);
        addPresenter(mGroupAuthPresenter);

        mGroupAuthPresenter.getGroupMemberList(mDoctorId, mGroupId);
    }

    @Override
    public void startAuthorizing(String message) {
        showProgressDialog("",message,null);
    }

    @Override
    public void authorizeSuccess(String message) {
        showShortToast(message);
        dismissProgressDialog();
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
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
        mRecyclerView.showNetworkErrorView(new CommonRecyclerView.OnNetworkErrorViewClickListener() {
            @Override
            public void onClick() {
                mGroupAuthPresenter.getGroupMemberList(mDoctorId, mGroupId);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mGroupAuthPresenter.getGroupMemberList(mDoctorId, mGroupId);
            }
        });
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mGroupAuthPresenter.getGroupMemberList(mDoctorId, mGroupId);
            }
        });
    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_confirm:
                if (null == mRVAdapter || mRVAdapter.getItemCount() <= 0) {
                    showShortToast("未选择授权医师");
                    break;
                }

                Observable.from(mRVAdapter.getBeanList())
                        .flatMap(new Func1<GroupDoctorBean, Observable<GroupAuthorizeBody.DtosEntity>>() {
                            @Override
                            public Observable<GroupAuthorizeBody.DtosEntity> call(GroupDoctorBean groupDoctorBean) {
                                GroupAuthorizeBody.DtosEntity entity = new GroupAuthorizeBody.DtosEntity();
                                entity.setDoctorId(groupDoctorBean.doctorId.get());
                                entity.setType(groupDoctorBean.isSelected.get() ? "Manager" : "Member");
                                return Observable.just(entity);
                            }
                        })
                        .subscribe(new Subscriber<GroupAuthorizeBody.DtosEntity>() {
                            GroupAuthorizeBody body = new GroupAuthorizeBody();
                            List<GroupAuthorizeBody.DtosEntity> doctors = new ArrayList<>();

                            @Override
                            public void onCompleted() {
                                body.setDtos(doctors);
                                mGroupAuthPresenter.authorize(AIManager.getInstance().getDoctorId(), mGroupId, body);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GroupAuthorizeBody.DtosEntity dtosEntity) {
                                doctors.add(dtosEntity);
                            }
                        });
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
