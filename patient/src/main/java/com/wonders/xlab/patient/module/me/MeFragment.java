package com.wonders.xlab.patient.module.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.me.setting.SettingActivity;
import com.wonders.xlab.patient.util.ImageViewManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {

    @Bind(R.id.iv_me_portrait)
    ImageView mIvMePortrait;
    @Bind(R.id.tv_me_setting)
    TextView mTvMeSetting;
    @Bind(R.id.tv_me_name)
    TextView mTvMeName;
    @Bind(R.id.tv_me_tel)
    TextView mTvMeTel;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment getInstance() {
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
        ImageViewManager.setImageViewWithUrl(getActivity(), mIvMePortrait, AIManager.getInstance().getPatientPortraitUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
        mTvMeName.setText(AIManager.getInstance().getPatientName());
        mTvMeTel.setText(String.format("账号：%s", AIManager.getInstance().getPatientTel()));
    }

    @OnClick(R.id.tv_me_setting)
    public void goToSettingActivity() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_me));
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_me));
    }
}
