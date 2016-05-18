package com.wonders.xlab.pci.doctor.module.patientinfo.basicinfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.recyclerview.VerticalItemDecoration;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.UserInfoPresenter;
import com.wonders.xlab.pci.doctor.module.patientinfo.basicinfo.adapter.UserInfoRVAdapter;
import com.wonders.xlab.pci.doctor.module.patientinfo.basicinfo.bean.UserInfoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends BaseFragment implements UserInfoPresenter.UserInfoPresenterListener {
    public final static String ARG_PATIENT_ID = "patientId";

    private String mPatientId;

    @Bind(R.id.recycler_view_user_info)
    CommonRecyclerView mRecyclerView;

    private UserInfoRVAdapter mUserInfoRVAdapter;

    private UserInfoPresenter mUserInfoPresenter;


    public UserInfoFragment() {
        // Required empty public constructor
    }

    public static UserInfoFragment newInstance(String patientId) {
        Bundle data = new Bundle();
        data.putString(ARG_PATIENT_ID, patientId);
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        mPatientId = data.getString(ARG_PATIENT_ID);
        if (TextUtils.isEmpty(mPatientId)) {
            showShortToast("获取患者信息失败，请重试！");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_info_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(getActivity(), getResources().getColor(R.color.divider), 1));
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserInfoPresenter.getUserInfo(mPatientId);
            }
        });

        mUserInfoPresenter = new UserInfoPresenter(this);
        addPresenter(mUserInfoPresenter);

        mUserInfoPresenter.getUserInfo(mPatientId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUserInfoRVAdapter = null;
        ButterKnife.unbind(this);
    }

    @Override
    public void showUserInfo(List<UserInfoBean> userInfoBeanList) {
        if (mUserInfoRVAdapter == null) {
            mUserInfoRVAdapter = new UserInfoRVAdapter();
        }
        mUserInfoRVAdapter.setDatas(userInfoBeanList);
        mRecyclerView.setAdapter(mUserInfoRVAdapter);

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
                mUserInfoPresenter.getUserInfo(mPatientId);
            }
        }, R.id.btn_common_network_error_retry);
    }

    @Override
    public void showServerError(String message) {
        mRecyclerView.showServerErrorView(new CommonRecyclerView.OnServerErrorViewClickListener() {
            @Override
            public void onClick() {
                mUserInfoPresenter.getUserInfo(mPatientId);
            }
        }, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showEmptyView(String message) {
        mRecyclerView.showEmptyView(new CommonRecyclerView.OnEmptyViewClickListener() {
            @Override
            public void onClick() {
                mUserInfoPresenter.getUserInfo(mPatientId);
            }
        }, true, CommonRecyclerView.HANDLE_VIEW_ID_NONE);
    }

    @Override
    public void showErrorToast(String message) {
        showShortToast(message);
    }

    @Override
    public void hideLoading() {
        mRecyclerView.hideRefreshOrLoadMore(true, true);
    }
}
