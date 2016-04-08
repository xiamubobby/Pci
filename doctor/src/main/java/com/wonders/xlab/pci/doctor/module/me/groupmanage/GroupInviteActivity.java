package com.wonders.xlab.pci.doctor.module.me.groupmanage;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.base.AppbarActivity;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupInviteRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.GroupInviteSelectedDoctorRVAdapter;
import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupInviteDoctorBean;
import com.wonders.xlab.pci.doctor.mvp.presenter.IGroupInvitePresenter;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.GroupInvitePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.uikit.crv.CommonRecyclerView;
import im.hua.utils.DensityUtil;
import im.hua.utils.KeyboardUtil;

public class GroupInviteActivity extends AppbarActivity implements GroupInvitePresenter.GroupInvitePresenterListener {
    public final static int RESULT_CODE_SUCCESS = 0;
    public final static String EXTRA_RESULT = "result";

    @Bind(R.id.recycler_view_group_invite)
    CommonRecyclerView mRecyclerView;
    @Bind(R.id.iv_group_invite_search)
    ImageView mIvSearch;
    @Bind(R.id.recycler_view_group_invite_selected_doctor)
    RecyclerView mRecyclerViewSelectedDoctor;
    @Bind(R.id.et_group_invite_search)
    EditText mEtSearch;

    private GroupInviteRVAdapter mGroupInviteRVAdapter;
    private GroupInviteSelectedDoctorRVAdapter mSelectedDoctorRVAdapter;

    private IGroupInvitePresenter mGroupInvitePresenter;

    @Override
    public int getContentLayout() {
        return R.layout.group_invite_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mMinEtSearchWidth = DensityUtil.dp2px(this, 72);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || s.length() < 2 || (TextUtils.isDigitsOnly(s.toString()) && s.length() != 11)) {
                    return;
                }
                mGroupInvitePresenter.searchByNameOrTel(s.toString());
            }
        });

        mRecyclerViewSelectedDoctor.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewSelectedDoctor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this, getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyboardUtil.hide(GroupInviteActivity.this);
                return false;
            }
        });
        mGroupInvitePresenter = new GroupInvitePresenter(this);
        addPresenter(mGroupInvitePresenter);
    }

    private int mMaxShowSelectedDoctorCounts = -1;
    private int mMinEtSearchWidth;
    private LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    @Override
    public void showDoctorList(List<GroupInviteDoctorBean> doctorBeanList) {
        if (null == mGroupInviteRVAdapter) {
            mGroupInviteRVAdapter = new GroupInviteRVAdapter();
            mGroupInviteRVAdapter.setOnItemClickListener(new SimpleRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    KeyboardUtil.hide(GroupInviteActivity.this);

                    //1、更改勾选状态
                    GroupInviteDoctorBean bean = mGroupInviteRVAdapter.getBean(position);
                    bean.isSelected.set(!bean.isSelected.get());
                    mGroupInviteRVAdapter.notifyItemChanged(position);

                    //2、更新顶部已选中的医生列表
                    if (null == mSelectedDoctorRVAdapter) {
                        mSelectedDoctorRVAdapter = new GroupInviteSelectedDoctorRVAdapter();
                        mRecyclerViewSelectedDoctor.setAdapter(mSelectedDoctorRVAdapter);
                    }

                    //4、控制输入区域的最小宽度
                    ViewGroup.LayoutParams layoutParams;

                    if (bean.isSelected.get()) {
                        mSelectedDoctorRVAdapter.appendToLast(bean);
                        mRecyclerViewSelectedDoctor.smoothScrollToPosition(mSelectedDoctorRVAdapter.getItemCount() - 1);

                        int rvOriginWidth = mRecyclerViewSelectedDoctor.getWidth();
                        int width = mEtSearch.getMeasuredWidth();
                        if (width < mMinEtSearchWidth) {
                            if (-1 == mMaxShowSelectedDoctorCounts) {
                                mMaxShowSelectedDoctorCounts = mSelectedDoctorRVAdapter.getItemCount();
                            }

                            layoutParams = mRecyclerViewSelectedDoctor.getLayoutParams();
                            if (null == layoutParams) {
                                layoutParams = new ViewGroup.LayoutParams(rvOriginWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                            } else {
                                layoutParams.width = rvOriginWidth;
                            }
                            mRecyclerViewSelectedDoctor.setLayoutParams(layoutParams);

                        }
                    } else {
                        mSelectedDoctorRVAdapter.unselectedDoctor(bean);

                        if (mSelectedDoctorRVAdapter.getItemCount() > 0 && mSelectedDoctorRVAdapter.getItemCount() < mMaxShowSelectedDoctorCounts) {
                            mRecyclerViewSelectedDoctor.setLayoutParams(defaultLayoutParams);
                        }
                    }

                    //3、更新搜索图标，选择了医生了则隐藏，否则显示
                    if (mSelectedDoctorRVAdapter.getItemCount() <= 0) {
                        mIvSearch.setVisibility(View.VISIBLE);
                    } else {
                        mIvSearch.setVisibility(View.GONE);
                    }


                }
            });
            mRecyclerView.setAdapter(mGroupInviteRVAdapter);
        }
        if (null != mSelectedDoctorRVAdapter) {
            for (GroupInviteDoctorBean bean : mSelectedDoctorRVAdapter.getBeanList()) {
                int indexOf = doctorBeanList.indexOf(bean);
                if (-1 != indexOf) {
                    doctorBeanList.get(indexOf).isSelected.set(true);
                }
            }
        }
        mGroupInviteRVAdapter.setDatas(doctorBeanList);
    }

    @Override
    public void appendDoctorList(List<GroupInviteDoctorBean> doctorBeanList) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showReachTheLastPageNotice(String message) {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSelectedDoctorRVAdapter = null;
        mGroupInviteRVAdapter = null;
    }
}
