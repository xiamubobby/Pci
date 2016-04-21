package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateMemberBody;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupDoctorMultiChoiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupInviteSelectedDoctorRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.IGroupInviteDoctorPresenter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.impl.GroupDoctorInvitePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.KeyboardUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class GroupInviteDoctorActivity extends AppbarActivity implements GroupDoctorInvitePresenter.GroupInvitePresenterListener {
    public final static int RESULT_CODE_SUCCESS = 12345;
    public final static String EXTRA_RESULT_OWNER_ID = "ownerId";

    public final static String EXTRA_OWNER_ID = "ownerId";
    private String mOwnerId;

    @Bind(R.id.recycler_view_group_invite)
    CommonRecyclerView mRecyclerView;
    @Bind(R.id.iv_group_invite_search)
    ImageView mIvSearch;
    @Bind(R.id.recycler_view_group_invite_selected_doctor)
    RecyclerView mRecyclerViewSelectedDoctor;
    @Bind(R.id.et_group_invite_search)
    EditText mEtSearch;

    private GroupDoctorMultiChoiceRVAdapter mGroupDoctorMultiChoiceRVAdapter;
    private GroupInviteSelectedDoctorRVAdapter mSelectedDoctorRVAdapter;

    private IGroupInviteDoctorPresenter mGroupInvitePresenter;

    @Override
    public int getContentLayout() {
        return R.layout.group_invite_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mOwnerId = intent.getStringExtra(EXTRA_OWNER_ID);
        }

        RxTextView.afterTextChangeEvents(mEtSearch)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<TextViewAfterTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        Editable s = textViewAfterTextChangeEvent.editable();
                        return ((!TextUtils.isDigitsOnly(s.toString()) && !TextUtils.isEmpty(s.toString()) && s.length() >= 2) || (TextUtils.isDigitsOnly(s.toString()) && s.length() >= 11));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        String s = textViewAfterTextChangeEvent.editable().toString();
                        mRecyclerView.setRefreshing(true);
                        mGroupInvitePresenter.searchByNameOrTel(AIManager.getInstance().getDoctorId(), mOwnerId, s);
                    }
                });

        mRecyclerViewSelectedDoctor.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewSelectedDoctor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyboardUtil.hide(GroupInviteDoctorActivity.this);
                return false;
            }
        });
        mGroupInvitePresenter = new GroupDoctorInvitePresenter(this);
        addPresenter(mGroupInvitePresenter);
        mRecyclerView.showEmptyView(null);
    }

    @Override
    public void showDoctorList(List<GroupDoctorBean> doctorBeanList) {
        if (null == mGroupDoctorMultiChoiceRVAdapter) {
            mGroupDoctorMultiChoiceRVAdapter = new GroupDoctorMultiChoiceRVAdapter();
            mGroupDoctorMultiChoiceRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {

                    GroupDoctorBean bean = mGroupDoctorMultiChoiceRVAdapter.getBean(position);

                    //1、更新顶部已选中的医生列表
                    if (null == mSelectedDoctorRVAdapter) {
                        mSelectedDoctorRVAdapter = new GroupInviteSelectedDoctorRVAdapter();
                        mRecyclerViewSelectedDoctor.setAdapter(mSelectedDoctorRVAdapter);
                    }

                    if (bean.isSelected.get()) {
                        mSelectedDoctorRVAdapter.appendToLast(bean);
                        mRecyclerViewSelectedDoctor.smoothScrollToPosition(mSelectedDoctorRVAdapter.getItemCount() - 1);

                    } else {
                        mSelectedDoctorRVAdapter.unselectedDoctor(bean);
                    }

                    //2、更新搜索图标，选择了医生了则隐藏，否则显示
                    if (mSelectedDoctorRVAdapter.getItemCount() <= 0) {
                        mIvSearch.setVisibility(View.VISIBLE);
                    } else {
                        mIvSearch.setVisibility(View.GONE);
                    }
                }
            });
            mRecyclerView.setAdapter(mGroupDoctorMultiChoiceRVAdapter);
        }
        if (null != mSelectedDoctorRVAdapter) {
            for (GroupDoctorBean bean : mSelectedDoctorRVAdapter.getBeanList()) {
                int indexOf = doctorBeanList.indexOf(bean);
                if (-1 != indexOf) {
                    doctorBeanList.get(indexOf).isSelected.set(true);
                }
            }
        }
        mGroupDoctorMultiChoiceRVAdapter.setDatas(doctorBeanList);
    }

    @Override
    public void inviteDoctorSuccess(String ownerId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT_OWNER_ID, ownerId);
        setResult(RESULT_CODE_SUCCESS, intent);
        finish();
    }

    @Override
    public void showLoading(String message) {
        mRecyclerView.setRefreshing(true);
    }

    @Override
    public void showNetworkError(String message) {
        mRecyclerView.showNetworkErrorView(null);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(null);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(null);
    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
        dismissProgressDialog();
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

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
                if (null == mSelectedDoctorRVAdapter || mSelectedDoctorRVAdapter.getItemCount() <= 0) {
                    finish();
                    break;
                }

                Observable.from(mSelectedDoctorRVAdapter.getBeanList())
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
                                GroupUpdateMemberBody.Owner owner = new GroupUpdateMemberBody.Owner();
                                owner.setId(mOwnerId);
                                body.setOwner(owner);
                                showProgressDialog("","正在发送邀请，请稍候...", null);
                                mGroupInvitePresenter.inviteDoctors(AIManager.getInstance().getDoctorId(), body);
                            }

                            @Override
                            public void onError(Throwable e) {

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
        mSelectedDoctorRVAdapter = null;
        mGroupDoctorMultiChoiceRVAdapter = null;
    }
}
