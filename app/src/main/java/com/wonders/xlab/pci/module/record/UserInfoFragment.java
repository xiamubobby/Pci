package com.wonders.xlab.pci.module.record;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.BaseFragment;
import com.wonders.xlab.pci.module.record.adapter.UserInfoRVAdapter;
import com.wonders.xlab.pci.module.record.bean.UserInfoBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends BaseFragment {

    @Bind(R.id.rv_user_info)
    RecyclerView mRvUserInfo;

    private UserInfoRVAdapter mUserInfoRVAdapter;

    public UserInfoFragment() {
        // Required empty public constructor
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
        mUserInfoRVAdapter = new UserInfoRVAdapter(new WeakReference<Context>(getActivity()));
        List<UserInfoBean> userInfoBeanList = new ArrayList<>();
        userInfoBeanList.add(new UserInfoBean("姓名", "六二"));
        userInfoBeanList.add(new UserInfoBean("病历号", "123456"));
        userInfoBeanList.add(new UserInfoBean("医保号", ""));
        userInfoBeanList.add(new UserInfoBean("性别", ""));
        userInfoBeanList.add(new UserInfoBean("身份证号", ""));
        userInfoBeanList.add(new UserInfoBean("住址", ""));
        userInfoBeanList.add(new UserInfoBean("邮编", ""));
        userInfoBeanList.add(new UserInfoBean("电话", ""));
        userInfoBeanList.add(new UserInfoBean("婚姻", ""));
        userInfoBeanList.add(new UserInfoBean("民族", ""));
        userInfoBeanList.add(new UserInfoBean("身高", ""));
        userInfoBeanList.add(new UserInfoBean("体重", ""));
        userInfoBeanList.add(new UserInfoBean("BMI值", ""));
        userInfoBeanList.add(new UserInfoBean("心率", ""));
        userInfoBeanList.add(new UserInfoBean("血压", ""));
        mUserInfoRVAdapter.setDatas(userInfoBeanList);
        mRvUserInfo.setAdapter(mUserInfoRVAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
