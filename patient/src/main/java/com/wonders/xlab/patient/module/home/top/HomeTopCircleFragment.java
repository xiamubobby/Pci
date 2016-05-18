package com.wonders.xlab.patient.module.home.top;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.dailyreport.DailyReportActivity;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BPReportRealmBean;
import com.wonders.xlab.patient.module.dailyreport.adapter.bean.BSReportRealmBean;
import com.wonders.xlab.patient.otto.BPSaveSuccessOtto;
import com.wonders.xlab.patient.otto.BSSaveSuccessOtto;
import com.wonders.xlab.patient.util.UmengEventId;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;
import im.hua.utils.DensityUtil;

public class HomeTopCircleFragment extends BaseFragment implements HomeTopPresenterContract.ViewListener {
    @Bind(R.id.tv_home_top_circle_left)
    TextView mTvLeft;
    @Bind(R.id.tv_home_top_circle_middle_status)
    TextView mTvMiddleStatus;
    @Bind(R.id.tv_home_top_circle_middle_value)
    TextView mTvMiddleValue;
    @Bind(R.id.ll_home_top_circle_middle)
    LinearLayout mLlMiddle;
    @Bind(R.id.tv_home_top_circle_right)
    TextView mTvRight;

    private HomeTopPresenterContract.Actions mTopPresenter;


    public HomeTopCircleFragment() {
        // Required empty public constructor
    }

    public static HomeTopCircleFragment newInstance() {
        return new HomeTopCircleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OttoManager.register(this);

        mTopPresenter = DaggerHomeTopComponent.builder()
                .applicationComponent(((XApplication) getActivity().getApplication()).getComponent())
                .homeTopModule(new HomeTopModule(this))
                .build()
                .getHomeTopPresenter();

        addPresenter(mTopPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_top_circle_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int gapIntervalInPix = DensityUtil.dp2px(getActivity(), 8);//px

        int width = DensityUtil.getWindowWidthPixel(getActivity()) - 4 * gapIntervalInPix;

        RelativeLayout.LayoutParams middleLp = (RelativeLayout.LayoutParams) mLlMiddle.getLayoutParams();
        middleLp.width = width / 2;
        middleLp.height = middleLp.width;
        mLlMiddle.setLayoutParams(middleLp);

        RelativeLayout.LayoutParams leftLp = (RelativeLayout.LayoutParams) mTvLeft.getLayoutParams();
        RelativeLayout.LayoutParams rightLp = (RelativeLayout.LayoutParams) mTvRight.getLayoutParams();

        leftLp.width = middleLp.width / 2;
        leftLp.height = leftLp.width;
        rightLp.width = leftLp.width;
        rightLp.height = rightLp.width;

        mTvLeft.setLayoutParams(leftLp);
        mTvRight.setLayoutParams(rightLp);

        refreshTopBP(null);
        refreshTopBS(null);
    }

    @OnClick(R.id.ll_home_top_circle_middle)
    public void onBPClick() {
        MobclickAgent.onEvent(getActivity(), UmengEventId.HOME_TOP_CIRCLE_BP);
        goToDailyRecordActivity(DailyReportActivity.SHOW_TAB_POSITION_BP);
    }

    @OnClick(R.id.tv_home_top_circle_right)
    public void onHeartRateClick() {
        MobclickAgent.onEvent(getActivity(), UmengEventId.HOME_TOP_CIRCLE_HEART_RATE);
        goToDailyRecordActivity(DailyReportActivity.SHOW_TAB_POSITION_BP);
    }

    @OnClick(R.id.tv_home_top_circle_left)
    public void onBSClick() {
        MobclickAgent.onEvent(getActivity(), UmengEventId.HOME_TOP_CIRCLE_BS);
        goToDailyRecordActivity(DailyReportActivity.SHOW_TAB_POSITION_BS);
    }

    private void goToDailyRecordActivity(int position) {
        Intent intent = new Intent(getActivity(), DailyReportActivity.class);
        intent.putExtra(DailyReportActivity.DEFAULT_SHOW_TAB_POSITION, position);
        startActivity(intent);
    }

    @Subscribe
    public void refreshTopBP(BPSaveSuccessOtto otto) {
        mTopPresenter.getRecentBp();
    }

    @Subscribe
    public void refreshTopBS(BSSaveSuccessOtto otto) {
        mTopPresenter.getRecentBs();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showRecentBs(BSReportRealmBean bean) {
        if (null != bean) {
            if (bean.getBloodSugarStatus() == 0) {
                mTvLeft.setText(String.format("正常\n%s\nmmol/L", bean.getBloodSugar()));
                mTvLeft.setTextColor(Color.parseColor("#006b93"));
            } else {
                SpannableString msp = new SpannableString(String.format("异常\n%s\nmmol/L", bean.getBloodSugar()));
                msp.setSpan(new ForegroundColorSpan(Color.RED), 0, "异常".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvLeft.setText(msp);
            }
        } else {
            mTvLeft.setText("记录\n0\nmmol/L");
            mTvLeft.setTextColor(Color.parseColor("#006b93"));
        }
    }

    @Override
    public void showRecentBp(BPReportRealmBean bean) {
        if (null != bean) {
            //血压
            if (bean.getHighPressureStatus() == 0 && bean.getLowPressureStatus() == 0) {
                mTvMiddleStatus.setText("正常");
                mTvMiddleStatus.setTextColor(Color.WHITE);
            } else {
                mTvMiddleStatus.setText("异常");
                mTvMiddleStatus.setTextColor(Color.RED);
            }
            mTvMiddleValue.setText(String.format("%s/%s\nmmHg", bean.getHighPressure(), bean.getLowPressure()));

            //心率
            if (bean.getHeartRateStatus() == 0) {
                mTvRight.setText(String.format("正常\n%s\n次/分", bean.getHeartRate()));
                mTvRight.setTextColor(Color.parseColor("#006b93"));
            } else {
                SpannableString msp = new SpannableString(String.format("异常\n%s\n次/分", bean.getHeartRate()));
                msp.setSpan(new ForegroundColorSpan(Color.RED), 0, "异常".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvRight.setText(msp);
            }
        } else {
            mTvMiddleStatus.setText("点击记录");
            mTvMiddleStatus.setTextColor(Color.WHITE);
            mTvMiddleValue.setText("0/0\nmmHg");

            mTvRight.setText("记录\n0\n次/分");
            mTvRight.setTextColor(Color.parseColor("#006b93"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OttoManager.unregister(this);
    }
}