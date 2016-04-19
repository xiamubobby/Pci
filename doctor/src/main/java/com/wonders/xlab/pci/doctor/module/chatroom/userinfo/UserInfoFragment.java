package com.wonders.xlab.pci.doctor.module.chatroom.userinfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.data.presenter.impl.UserInfoPresenter;
import com.wonders.xlab.pci.doctor.module.chatroom.userinfo.adapter.UserInfoRVAdapter;

import butterknife.Bind;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.crv.CommonRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends BaseFragment {
    public final static String EXTRA_PATIENT_ID = "patientId";

    private String mPatientId;

    @Bind(R.id.recycler_view_user_info)
    CommonRecyclerView mRecyclerView;

    private UserInfoRVAdapter mUserInfoRVAdapter;

    private UserInfoPresenter mUserInfoPresenter;


    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        mPatientId = data.getString(EXTRA_PATIENT_ID);
        if (TextUtils.isEmpty(mPatientId)) {
            showShortToast("获取患者信息失败，请重试！");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

}
