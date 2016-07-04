package com.wonders.xlab.patient.module.me;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.me.about.AboutUsActivity;
import com.wonders.xlab.patient.module.me.otto.UserIconUpdateOtto;
import com.wonders.xlab.patient.module.me.otto.UserInfoUpdateOtto;
import com.wonders.xlab.patient.module.me.setting.SettingActivity;
import com.wonders.xlab.patient.module.me.userinfo.UserInfoActivity;
import com.wonders.xlab.patient.module.order.OrderListActivity;
import com.wonders.xlab.patient.otto.ForceExitOtto;
import com.wonders.xlab.patient.util.ImageViewManager;

import java.io.File;

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
    @Bind(R.id.tv_tel_num)
    TextView serviceTel;

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
        OttoManager.register(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showUserInfo();

    }

    private void showUserInfo() {
        ImageViewManager.setImageViewWithUrl(getActivity(), mIvMePortrait, AIManager.getInstance().getPatientPortraitUrl(), ImageViewManager.PLACE_HOLDER_EMPTY);
        mTvMeName.setText(AIManager.getInstance().getPatientName());
        if (!TextUtils.isEmpty(AIManager.getInstance().getPatientSex())) {
            mTvMeSex.setText("性别：" + AIManager.getInstance().getPatientSex());
        } else {
            mTvMeSex.setText("性别：暂无");
        }
        if (!TextUtils.isEmpty(AIManager.getInstance().getPatientSex())) {
            mTvMeAge.setText("年龄：" + AIManager.getInstance().getPatientAge());
        } else {
            mTvMeAge.setText("年龄：暂无");
        }
    }

    @Subscribe
    public void refreshUserInfo(UserInfoUpdateOtto otto) {
        if (!TextUtils.isEmpty(otto.getSex())) {
            if (mTvMeSex != null) {
                mTvMeSex.setText("性别：" + otto.getSex());
            }
        }
        if (!TextUtils.isEmpty(otto.getAge())) {
            if (mTvMeAge != null) {
                mTvMeAge.setText("年龄：" + otto.getAge());
            }
        }
    }

    @Subscribe
    public void refreshUserIcon(UserIconUpdateOtto otto) {
        if (mIvMePortrait != null) {
            File mPickedIdPicFile = new File(otto.getUri());
            Uri uri = Uri.fromFile(mPickedIdPicFile);
            ImageViewManager.setImageViewWithUri(getActivity(), mIvMePortrait, uri, ImageViewManager.PLACE_HOLDER_EMPTY);
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

    @OnClick(R.id.rl_service_tel)
    public void makePhone() {
        Intent it = new Intent(Intent.ACTION_DIAL);
        it.setData(Uri.parse("tel:4001121881"));
        startActivity(it);
    }

    @OnClick(R.id.tv_me_about_us)
    public void goToAboutUsActivity() {
        startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }

    @OnClick(R.id.btn_me_exit)
    public void exit() {
        OttoManager.post(new ForceExitOtto());
        getActivity().finish();
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
