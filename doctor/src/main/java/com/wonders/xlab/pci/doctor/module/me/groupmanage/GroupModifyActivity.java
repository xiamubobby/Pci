package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateBasicInfoBody;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupModifyMemberRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupServiceIconRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupModifyMemberBean;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.GroupServicesActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.IGroupModifyPresenter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter.impl.GroupModifyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class GroupModifyActivity extends AppbarActivity implements GroupModifyPresenter.GroupModifyPresenterListener {
    private final int REQUEST_CODE_NAME = 0;
    private final int REQUEST_CODE_DESC = 1;
    private final int REQUEST_CODE_INVITE_DOCTOR = 2;
    private final int REQUEST_CODE_REMOVE_DOCTOR = 3;
    private final int REQUEST_CODE_AUTH = 4;//授权
    private final int REQUEST_CODE_SERVICE = 5;//套餐

    public final static int RESULT_CODE_SUCCESS = 0;

    public final static String EXTRA_GROUP_ID = "groupId";
    public final static String EXTRA_OWNER_ID = "ownerId";
    private String mOwnerId;

    /**
     * 未做修改点保存则直接返回
     */
    private boolean mHasChanged = false;

    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @Bind(R.id.recycler_view_group_modify_members)
    RecyclerView mRecyclerViewMembers;
    @Bind(R.id.recycler_view_group_modify_service)
    RecyclerView mRecyclerViewService;
    @Bind(R.id.tv_group_modify_group_name)
    TextView mTvGroupName;
    @Bind(R.id.tv_group_modify_group_desc)
    TextView mTvGroupDesc;

    @Bind(R.id.tv_group_modify_group_desc_trigger)
    TextView mTvGroupDescTrigger;
    @Bind(R.id.ll_group_modify_service)
    LinearLayout mLlService;
    @Bind(R.id.tv_group_modify_auth)
    TextView mTvAuth;
    @Bind(R.id.btn_group_modify_dismiss)
    Button mBtnDismiss;

    private GroupModifyMemberRVAdapter mMemberRVAdapter;

    private GroupServiceIconRVAdapter mServiceIconRVAdapter;

    private IGroupModifyPresenter mGroupModifyPresenter;

    /**
     * 当前医生是否为管理员
     */
    private boolean mIsAdmin = false;

    @Override
    public int getContentLayout() {
        return R.layout.group_modify_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null != intent) {
            mOwnerId = intent.getStringExtra(EXTRA_OWNER_ID);
        }

        if (TextUtils.isEmpty(mOwnerId)) {
            setToolbarTitle("新建小组");
            mBtnDismiss.setVisibility(View.GONE);
            /**
             * 小组名称模板
             */
            mTvGroupName.setText(String.format("%s医师诊所", AIManager.getInstance().getDoctorName()));
        }
        mGroupModifyPresenter = new GroupModifyPresenter(this);
        addPresenter(mGroupModifyPresenter);

        mRecyclerViewMembers.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewMembers.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewService.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        mRecyclerViewService.setItemAnimator(new DefaultItemAnimator());

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGroupModifyPresenter.getGroupInfo(AIManager.getInstance().getDoctorId(), mOwnerId);
            }
        });

        RxView.clicks(mTvGroupDescTrigger)
                .throttleFirst(Constant.VIEW_CLICK_SKIP_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent nameIntent = new Intent("com.wonders.xlab.pci.doctor.GroupDescModifyActivity");
                        nameIntent.putExtra(GroupNameModifyActivity.EXTRA_HINT, "小组简介");
                        nameIntent.putExtra(GroupNameModifyActivity.EXTRA_TEXT, mTvGroupDesc.getText().toString());
                        startActivityForResult(nameIntent, REQUEST_CODE_DESC);
                    }
                });
        RxView.clicks(mTvGroupDesc)
                .throttleFirst(Constant.VIEW_CLICK_SKIP_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent nameIntent = new Intent("com.wonders.xlab.pci.doctor.GroupDescModifyActivity");
                        nameIntent.putExtra(GroupNameModifyActivity.EXTRA_HINT, "小组简介");
                        nameIntent.putExtra(GroupNameModifyActivity.EXTRA_TEXT, mTvGroupDesc.getText().toString());
                        startActivityForResult(nameIntent, REQUEST_CODE_DESC);
                    }
                });
        RxView.clicks(mTvAuth)
                .throttleFirst(Constant.VIEW_CLICK_SKIP_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent nameIntent = new Intent("com.wonders.xlab.pci.doctor.GroupAuthActivity");
                        nameIntent.putExtra(GroupAuthActivity.EXTRA_OWNER_ID, mOwnerId);
                        startActivityForResult(nameIntent, REQUEST_CODE_AUTH);
                    }
                });
        RxView.clicks(mLlService)
                .throttleFirst(Constant.VIEW_CLICK_SKIP_DURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent serviceIntent = new Intent("com.wonders.xlab.pci.doctor.GroupServicesActivity");
                        serviceIntent.putExtra(GroupServicesActivity.EXTRA_OWNER_ID, mOwnerId);
                        serviceIntent.putExtra(GroupServicesActivity.EXTRA_IS_ADMIN, mIsAdmin);
                        startActivityForResult(serviceIntent, REQUEST_CODE_SERVICE);
                    }
                });

        mGroupModifyPresenter.getGroupInfo(AIManager.getInstance().getDoctorId(), mOwnerId);
    }

    @Override
    public void showAdminOperationView() {
        mIsAdmin = true;
        invalidateOptionsMenu();

        Drawable drawable = getResources().getDrawable(R.drawable.ic_edit_pencil);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        mTvGroupDescTrigger.setCompoundDrawables(null, null, drawable, null);

        mTvGroupDescTrigger.setClickable(mIsAdmin);
        mTvGroupDesc.setClickable(mIsAdmin);

        //TODO
//        mBtnDismiss.setVisibility(mIsAdmin ? View.VISIBLE : View.GONE);
        mBtnDismiss.setVisibility(View.GONE);

        mMemberRVAdapter.setIsAdmin(mIsAdmin);
    }

    @Override
    public void hideAdminOperationView() {
        mIsAdmin = false;
        mTvGroupDescTrigger.setCompoundDrawables(null, null, null, null);

        mTvGroupDescTrigger.setClickable(mIsAdmin);
        mTvGroupDesc.setClickable(mIsAdmin);

        mMemberRVAdapter.setIsAdmin(mIsAdmin);
    }

    @Override
    public void showGroupInfo(GroupModifyBean groupModifyBean) {

        if (!TextUtils.isEmpty(groupModifyBean.getGroupName())) {
            mTvGroupName.setText(groupModifyBean.getGroupName());
        }

        mTvGroupDesc.setText(groupModifyBean.getGroupDesc());
        mTvAuth.setVisibility(groupModifyBean.isCanGrant() ? View.VISIBLE : View.GONE);

        if (null == mMemberRVAdapter) {
            mMemberRVAdapter = new GroupModifyMemberRVAdapter();
            mMemberRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (null == mMemberRVAdapter) {
                        return;
                    }
                    GroupModifyMemberBean bean = mMemberRVAdapter.getBean(position);
                    switch (bean.getType()) {
                        case GroupModifyMemberBean.TYPE_ADD:
                            Intent a = new Intent("com.wonders.xlab.pci.doctor.GroupInviteDoctorActivity");
                            a.putExtra(GroupInviteDoctorActivity.EXTRA_OWNER_ID, mOwnerId);
                            startActivityForResult(a, REQUEST_CODE_INVITE_DOCTOR);
                            break;
                        case GroupModifyMemberBean.TYPE_MINUS:
                            Intent intent = new Intent("com.wonders.xlab.pci.doctor.GroupRemoveDoctorActivity");
                            intent.putExtra(GroupRemoveDoctorActivity.EXTRA_OWNER_ID, mOwnerId);
                            startActivityForResult(intent, REQUEST_CODE_REMOVE_DOCTOR);
                            break;
                    }
                }
            });
            mRecyclerViewMembers.setAdapter(mMemberRVAdapter);
        }
        mMemberRVAdapter.setDatas(groupModifyBean.getMemberInfoList());

        if (null == mServiceIconRVAdapter) {
            mServiceIconRVAdapter = new GroupServiceIconRVAdapter();
            mRecyclerViewService.setAdapter(mServiceIconRVAdapter);
        }
        mServiceIconRVAdapter.setDatas(groupModifyBean.getPublishedServiceIconList());
    }

    @Override
    public void onGroupCreateSuccess(String newGroupId, String message) {
        showShortToast(message);
        setResult(RESULT_CODE_SUCCESS);
    }

    @Override
    public void cannotCreateGroup(String message) {
        if (TextUtils.isEmpty(mOwnerId)) {
            showShortToast(message);
            finish();
        }
    }

    @Override
    public void showLoading(String message) {
        setRefreshing(mRefresh, true);
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
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {
        setRefreshing(mRefresh, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data && requestCode != REQUEST_CODE_AUTH) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_NAME:
                if (resultCode == GroupNameModifyActivity.RESULT_CODE_SUCCESS) {
                    String name = data.getStringExtra(GroupNameModifyActivity.EXTRA_RESULT);
                    if (!TextUtils.isEmpty(name)) {
                        mHasChanged = true;
                        mTvGroupName.setText(name);
                    }
                }
                break;
            case REQUEST_CODE_DESC:
                if (resultCode == GroupDescModifyActivity.RESULT_CODE_SUCCESS) {
                    String name = data.getStringExtra(GroupNameModifyActivity.EXTRA_RESULT);
                    if (!TextUtils.isEmpty(name)) {
                        mHasChanged = true;
                        mTvGroupDesc.setText(name);
                    }
                }
                break;
            case REQUEST_CODE_INVITE_DOCTOR:
                if (resultCode == GroupInviteDoctorActivity.RESULT_CODE_SUCCESS) {
                    String tmpOwnerId = data.getStringExtra(GroupInviteDoctorActivity.EXTRA_RESULT_OWNER_ID);
                    if (!TextUtils.isEmpty(tmpOwnerId)) {
                        mOwnerId = tmpOwnerId;
                    }
                    mGroupModifyPresenter.getGroupInfo(AIManager.getInstance().getDoctorId(), mOwnerId);
                }
                break;
            case REQUEST_CODE_REMOVE_DOCTOR:
                if (resultCode == GroupRemoveDoctorActivity.RESULT_CODE_SUCCESS) {
                    mGroupModifyPresenter.getGroupInfo(AIManager.getInstance().getDoctorId(), mOwnerId);

                }
                break;
            case REQUEST_CODE_AUTH:
                if (resultCode == GroupAuthActivity.RESULT_CODE_SUCCESS) {
                    mGroupModifyPresenter.getGroupInfo(AIManager.getInstance().getDoctorId(), mOwnerId);
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsAdmin) {
            getMenuInflater().inflate(R.menu.menu_save, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (!mHasChanged) {
                    finish();
                    return true;
                }
                final GroupUpdateBasicInfoBody body = new GroupUpdateBasicInfoBody();
                body.setName(mTvGroupName.getText().toString());
                GroupUpdateBasicInfoBody.Owner owner = new GroupUpdateBasicInfoBody.Owner();
                owner.setId(mOwnerId);
                body.setOwner(owner);
                body.setDescription(mTvGroupDesc.getText().toString());

                mGroupModifyPresenter.createGroup(AIManager.getInstance().getDoctorId(), body);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMemberRVAdapter = null;
        mServiceIconRVAdapter = null;
    }
}
