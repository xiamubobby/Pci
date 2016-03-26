package com.wonders.xlab.patient.module.main.home;


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
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.application.XApplication;
import com.wonders.xlab.patient.module.healthreport.DailyRecordActivity;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BPReportBean;
import com.wonders.xlab.patient.module.healthreport.adapter.bean.BSReportBean;
import com.wonders.xlab.patient.module.healthreport.otto.BPSaveSuccessOtto;
import com.wonders.xlab.patient.module.healthreport.otto.BSSaveSuccessOtto;
import com.wonders.xlab.patient.module.main.home.bean.HomeTopCircleBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.hua.library.base.BaseFragment;
import im.hua.utils.DensityUtil;
import io.realm.RealmResults;
import io.realm.Sort;

public class HomeTopCircleFragment extends BaseFragment {
    private static final String ARG_VALUE_LIST = "valueList";
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

    private ArrayList<HomeTopCircleBean> mValueList;

    public HomeTopCircleFragment() {
        // Required empty public constructor
    }

    public static HomeTopCircleFragment newInstance(ArrayList<HomeTopCircleBean> valueList) {
        HomeTopCircleFragment fragment = new HomeTopCircleFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_VALUE_LIST, valueList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mValueList = getArguments().getParcelableArrayList(ARG_VALUE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);

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
        goToDailyRecordActivity(DailyRecordActivity.SHOW_TAB_POSITION_BP);
    }

    @OnClick(R.id.tv_home_top_circle_right)
    public void onHeartRateClick() {
        goToDailyRecordActivity(DailyRecordActivity.SHOW_TAB_POSITION_BP);
    }

    @OnClick(R.id.tv_home_top_circle_left)
    public void onBSClick() {
        goToDailyRecordActivity(DailyRecordActivity.SHOW_TAB_POSITION_BS);
    }

    private void goToDailyRecordActivity(int position) {
        Intent intent = new Intent(getActivity(), DailyRecordActivity.class);
        intent.putExtra(DailyRecordActivity.DEFAULT_SHOW_TAB_POSITION, position);
        startActivity(intent);
    }

    @Subscribe
    public void refreshTopBP(BPSaveSuccessOtto otto) {
        BPReportBean bpReportBean = getBPReportBean();
        if (null != bpReportBean) {
            //血压
            if (bpReportBean.getHighPressureStatus() == 0 && bpReportBean.getLowPressureStatus() == 0) {
                mTvMiddleStatus.setText("正常");
                mTvMiddleStatus.setTextColor(Color.WHITE);
            } else {
                mTvMiddleStatus.setText("异常");
                mTvMiddleStatus.setTextColor(Color.RED);
            }
            mTvMiddleValue.setText(String.format("%s/%s\nmmHg", bpReportBean.getHighPressure(), bpReportBean.getLowPressure()));

            //心率
            if (bpReportBean.getHeartRateStatus() == 0) {
                mTvRight.setText(String.format("正常\n%s\n次/分", bpReportBean.getHeartRate()));
                mTvRight.setTextColor(Color.parseColor("#006b93"));
            } else {
                SpannableString msp = new SpannableString(String.format("异常\n%s\n次/分", bpReportBean.getHeartRate()));
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

    @Subscribe
    public void refreshTopBS(BSSaveSuccessOtto otto) {
        BSReportBean bsReportBean = getBSReportBean();
        if (null != bsReportBean) {
            if (bsReportBean.getBloodSugarStatus() == 0) {
                mTvLeft.setText(String.format("正常\n%s\nmmol/L", bsReportBean.getBloodSugar()));
                mTvLeft.setTextColor(Color.parseColor("#006b93"));
            } else {
                SpannableString msp = new SpannableString(String.format("异常\n%s\nmmol/L", bsReportBean.getBloodSugar()));
                msp.setSpan(new ForegroundColorSpan(Color.RED), 0, "异常".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvLeft.setText(msp);
            }
        } else {
            mTvLeft.setText("记录\n0\nmmol/L");
            mTvLeft.setTextColor(Color.parseColor("#006b93"));
        }
    }

    /**
     * 获取最新的一条血压数据
     *
     * @return
     */
    private BPReportBean getBPReportBean() {
        XApplication.realm.beginTransaction();
        RealmResults<BPReportBean> beanRealmResults = XApplication.realm
                .where(BPReportBean.class)
                .equalTo("patientId", AIManager.getInstance(getActivity()).getPatientId())
                .findAllSorted("recordTimeInMill", Sort.DESCENDING);
        BPReportBean first;
        if (beanRealmResults.size() > 0) {
            first = beanRealmResults.first();
        } else {
            first = null;
        }
        XApplication.realm.commitTransaction();
        return first;
    }

    /**
     * 获取最新的一条血糖数据
     *
     * @return
     */
    private BSReportBean getBSReportBean() {
        XApplication.realm.beginTransaction();
        RealmResults<BSReportBean> beanRealmResults = XApplication.realm
                .where(BSReportBean.class)
                .equalTo("patientId", AIManager.getInstance(getActivity()).getPatientId())
                .findAllSorted("recordTimeInMill", Sort.DESCENDING);
        BSReportBean first;
        if (beanRealmResults.size() > 0) {
            first = beanRealmResults.first();
        } else {
            first = null;
        }
        XApplication.realm.commitTransaction();
        return first;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}
