package com.wonders.xlab.patient.module.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.me.about.AboutUsActivity;
import com.wonders.xlab.patient.module.me.setting.SettingActivity;
import com.wonders.xlab.patient.module.me.userinfo.UserInfoActivity;
import com.wonders.xlab.patient.module.order.OrderListActivity;
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
    @Bind(R.id.tv_me_name)
    TextView mTvMeName;
    @Bind(R.id.tv_me_sex)
    TextView mTvMeSex;
    @Bind(R.id.tv_me_age)
    TextView mTvMeAge;

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
        if (!TextUtils.isEmpty(AIManager.getInstance().getPatientSex())) {
            mTvMeSex.setText("性别：" + AIManager.getInstance().getPatientSex());
        } else {
            mTvMeSex.setText("性别：暂无");
        }
        if (!TextUtils.isEmpty(AIManager.getInstance().getPatientSex())) {
            mTvMeAge.setText("年龄：暂无");
        }

    }

    @OnClick(R.id.rl_me_user_info)
    public void goToUserInfoActivity() {
        startActivity(new Intent(getActivity(), UserInfoActivity.class));
    }

    @OnClick(R.id.tv_me_setting)
    public void goToSettingActivity() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @OnClick(R.id.tv_me_order)
    public void goToMyOrderListActivity() {
        startActivity(new Intent(getActivity(), OrderListActivity.class));
    }

    @OnClick(R.id.tv_me_about_us)
    public void goToAboutUsActivity() {
        startActivity(new Intent(getActivity(), AboutUsActivity.class));
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
