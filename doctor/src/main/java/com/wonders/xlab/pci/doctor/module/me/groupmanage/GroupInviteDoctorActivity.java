package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupDoctorMultiChoiceRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupInviteSelectedDoctorRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupInviteDoctorPresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupInviteDoctorPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.KeyboardUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class GroupInviteDoctorActivity extends AppbarActivity implements GroupInviteDoctorPresenter.GroupInvitePresenterListener {
    public final static int RESULT_CODE_SUCCESS = 0;
    public final static String EXTRA_RESULT = "result";

    public final static String EXTRA_GROUP_ID = "groupId";
    private String mGroupId;

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
            mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);
        }

        RxTextView.afterTextChangeEvents(mEtSearch)
                .throttleFirst(1000,TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<TextViewAfterTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        Editable s = textViewAfterTextChangeEvent.editable();
                        return ((TextUtils.isDigitsOnly(s.toString()) && TextUtils.isEmpty(s.toString()) || s.length() >= 2) || (TextUtils.isDigitsOnly(s.toString()) && s.length() == 11));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        String s = textViewAfterTextChangeEvent.editable().toString();
                        mRecyclerView.setRefreshing(true);
                        mGroupInvitePresenter.searchByNameOrTel(mGroupId, s);
                    }
                });

        mRecyclerViewSelectedDoctor.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewSelectedDoctor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyboardUtil.hide(GroupInviteDoctorActivity.this);
                return false;
            }
        });
        mGroupInvitePresenter = new GroupInviteDoctorPresenter(this);
        addPresenter(mGroupInvitePresenter);
        mRecyclerView.showEmptyView(null);
    }

    @Override
    public void showDoctorList(List<GroupDoctorBean> doctorBeanList) {
        if (null == mGroupDoctorMultiChoiceRVAdapter) {
            mGroupDoctorMultiChoiceRVAdapter = new GroupDoctorMultiChoiceRVAdapter();
            mGroupDoctorMultiChoiceRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    KeyboardUtil.hide(GroupInviteDoctorActivity.this);

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
    public void appendDoctorList(List<GroupDoctorBean> doctorBeanList) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {
        showShortToast(message);
    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true,true);
    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showEmptyView() {
        mRecyclerView.showEmptyView(null);
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
                if (null == mSelectedDoctorRVAdapter) {
                    finish();
                    return super.onOptionsItemSelected(item);
                }
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(EXTRA_RESULT, (ArrayList<? extends Parcelable>) mSelectedDoctorRVAdapter.getBeanList());
                setResult(RESULT_CODE_SUCCESS,intent);
                finish();
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
