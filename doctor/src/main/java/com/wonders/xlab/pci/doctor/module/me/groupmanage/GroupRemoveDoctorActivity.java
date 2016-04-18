package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupDoctorMultiChoiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateMemberBody;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.IGroupRemoveDoctorPresenter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.impl.GroupDoctorRemovePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class GroupRemoveDoctorActivity extends AppbarActivity implements GroupDoctorRemovePresenter.GroupRemoveDoctorPresenterListener {
    public final static int RESULT_CODE_SUCCESS = 12345;
    public final static String EXTRA_RESULT = "result";

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

        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRemoveDoctorPresenter = new GroupDoctorRemovePresenter(this);
        addPresenter(mRemoveDoctorPresenter);

        mRemoveDoctorPresenter.getCurrentMemberList(AIManager.getInstance().getDoctorId(), mGroupId);
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
    public void removeSuccess(String newGroupId) {
        dismissProgressDialog();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, newGroupId);
        setResult(RESULT_CODE_SUCCESS, intent);
        finish();
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mRemoveDoctorPresenter.getCurrentMemberList(AIManager.getInstance().getDoctorId(), mGroupId);
            }
        });
    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.showNetworkErrorView(new CommonRecyclerView.OnNetworkErrorViewClickListener() {
            @Override
            public void onClick() {
                mRemoveDoctorPresenter.getCurrentMemberList(AIManager.getInstance().getDoctorId(), mGroupId);
            }
        });
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mRemoveDoctorPresenter.getCurrentMemberList(AIManager.getInstance().getDoctorId(), mGroupId);
            }
        });
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
        dismissProgressDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_remove, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_remove:
                if (null == mMultiChoiceRVAdapter || mMultiChoiceRVAdapter.getItemCount() <= 0) {
                    finish();
                    break;
                }
                Observable.from(mMultiChoiceRVAdapter.getBeanList())
                        .filter(new Func1<GroupDoctorBean, Boolean>() {
                            @Override
                            public Boolean call(GroupDoctorBean groupDoctorBean) {
                                return groupDoctorBean.isSelected.get();
                            }
                        })
                        .flatMap(new Func1<GroupDoctorBean, Observable<GroupUpdateMemberBody.DoctorsEntity>>() {
                            @Override
                            public Observable<GroupUpdateMemberBody.DoctorsEntity> call(GroupDoctorBean groupDoctorBean) {
                                GroupUpdateMemberBody.DoctorsEntity entity = new GroupUpdateMemberBody.DoctorsEntity();
                                entity.setId(groupDoctorBean.doctorId.get());
                                entity.setImId(groupDoctorBean.doctorImId.get());
                                entity.setName(groupDoctorBean.doctorName.get());
                                return Observable.just(entity);
                            }
                        })
                        .subscribe(new Subscriber<GroupUpdateMemberBody.DoctorsEntity>() {
                            List<GroupUpdateMemberBody.DoctorsEntity> mDoctorsEntityList = new ArrayList<>();

                            @Override
                            public void onCompleted() {
                                GroupUpdateMemberBody body = new GroupUpdateMemberBody();
                                body.setDoctors(mDoctorsEntityList);
                                body.setId(mGroupId);
                                showProgressDialog("", "正在移除组员，请稍候...", null);
                                mRemoveDoctorPresenter.removeMembers(AIManager.getInstance().getDoctorId(), body);

                            }

                            @Override
                            public void onError(Throwable e) {
                                showShortToast(e.getMessage());
                            }

                            @Override
                            public void onNext(GroupUpdateMemberBody.DoctorsEntity doctorsEntity) {
                                mDoctorsEntityList.add(doctorsEntity);
                            }
                        });

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
