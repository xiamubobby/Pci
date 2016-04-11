package com.wonders.xlab.pci.doctor.module.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.common.manager.ImageViewManager;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.pci.doctor.R;
import com.wonders.xlab.pci.doctor.application.AIManager;
import com.wonders.xlab.pci.doctor.databinding.MeFragmentBinding;
import com.wonders.xlab.pci.doctor.otto.ForceExitOtto;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    @Bind(R.id.btn_me_exit)
    Button mBtnMeExit;
    @Bind(R.id.iv_me_portrait)
    ImageView mIvMePortrait;
    @Bind(R.id.tv_me_name)
    TextView mTvMeName;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MeFragmentBinding binding = MeFragmentBinding.bind(view);
        binding.setHandler(this);

        mTvMeName.setText(AIManager.getInstance().getDoctorName());
        ImageViewManager.setImageViewWithUrl(getActivity(), mIvMePortrait, AIManager.getInstance().getDoctorPortraitUrl(), R.drawable.portrait_default);
    }

    @OnClick(R.id.btn_me_exit)
    public void exit() {
        OttoManager.post(new ForceExitOtto());
        getActivity().finish();
    }

    public void groupManage(View view) {
        startActivity(new Intent("com.wonders.xlab.pci.doctor.GroupListActivity"));
    }

    public void notificationManage(View view) {
        startActivity(new Intent("com.wonders.xlab.pci.doctor.NotificationActivity"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
