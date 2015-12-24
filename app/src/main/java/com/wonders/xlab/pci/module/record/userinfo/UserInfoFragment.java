package com.wonders.xlab.pci.module.record.userinfo;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.utils.DateUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.application.AIManager;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.mvn.entity.record.userinfo.UserInfoEntity;
import com.wonders.xlab.pci.module.record.userinfo.mvn.model.UserInfoModel;
import com.wonders.xlab.pci.module.record.userinfo.mvn.view.UserInfoView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends BaseFragment implements UserInfoView {

    @Bind(R.id.rv_user_info)
    RecyclerView mRvUserInfo;
    @Bind(R.id.refresh_user_info)
    SwipeRefreshLayout mRefresh;

    private UserInfoRVAdapter mUserInfoRVAdapter;

    private UserInfoModel mUserInfoModel;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfoModel = new UserInfoModel(this);
        addModel(mUserInfoModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRvUserInfo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserInfoModel.getUserInfo(AIManager.getInstance(getActivity()).getUserId());
            }
        });
        mUserInfoModel.getUserInfo(AIManager.getInstance(getActivity()).getUserId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
            mUserInfoRVAdapter = new UserInfoRVAdapter(new WeakReference<Context>(getActivity()));
            mRvUserInfo.setAdapter(mUserInfoRVAdapter);
        } else {
            mUserInfoRVAdapter.clear();
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
}
