package com.wonders.xlab.patient.module.main.home;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.wonders.xlab.common.recyclerview.adapter.simple.SimpleRVAdapter;
import com.wonders.xlab.common.viewpager.LooperViewPager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.application.AIManager;
import com.wonders.xlab.patient.module.healthreport.HealthReportActivity;
import com.wonders.xlab.patient.module.main.home.adapter.HomeRVAdapter;
import com.wonders.xlab.patient.module.main.home.adapter.bean.HomeItemBean;
import com.wonders.xlab.patient.module.main.home.bean.HomeBannerBean;
import com.wonders.xlab.patient.module.main.home.dailyreport.DailyReportActivity;
import com.wonders.xlab.patient.module.main.home.medicalrecord.MedicalRecordActivity;
import com.wonders.xlab.patient.mvp.presenter.IHomeTopPresenter;
import com.wonders.xlab.patient.mvp.presenter.impl.HomeTopPresenter;
import com.wonders.xlab.patient.util.UmengEventId;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;
import im.hua.uikit.viewpager.CirclePageIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeTopPresenter.HomeTopPresenterListener {

    @Bind(R.id.view_pager_home)
    LooperViewPager mViewPagerHome;
    @Bind(R.id.recycler_view_home)
    RecyclerView mRecyclerViewHome;
    @Bind(R.id.indicator_home)
    CirclePageIndicator mIndicatorHome;

    private HomeRVAdapter homeRVAdapter;

    private FragmentVPAdapter topVPAdapter;

    private IHomeTopPresenter mIHomeTopPresenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);

        mIHomeTopPresenter = new HomeTopPresenter(this);
        addPresenter(mIHomeTopPresenter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewHome.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewHome.setItemAnimator(new DefaultItemAnimator());
        setupBottomFunctionView();

        if (null == topVPAdapter) {
            topVPAdapter = new FragmentVPAdapter(getActivity().getFragmentManager());
            mViewPagerHome.setAdapter(topVPAdapter);
            mIndicatorHome.setViewPager(mViewPagerHome);
            topVPAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    mIndicatorHome.notifyDataSetChanged();
                }
            });
        }
        topVPAdapter.addFragment(HomeTopCircleFragment.newInstance(null),"最近健康数据");

        mIHomeTopPresenter.getHomeBanner();

    }

    private void setupBottomFunctionView() {
        if (null == homeRVAdapter) {
            homeRVAdapter = new HomeRVAdapter();
            homeRVAdapter.setOnClickListener(new SimpleRVAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    switch (position) {
                        case 0:
                            MobclickAgent.onEvent(getActivity(), UmengEventId.HOME_DAILY_RECORD);
                            startActivity(new Intent(getActivity(), DailyReportActivity.class));
                            break;
                        case 1:
                            Intent intent = new Intent(getActivity(), MedicalRecordActivity.class);
                            intent.putExtra(MedicalRecordActivity.EXTRA_PATIENT_ID, AIManager.getInstance().getPatientId());
                            startActivity(intent);
                            break;
                        case 3:
                            startActivity(new Intent(getActivity(), HealthReportActivity.class));
                            break;
                        default:
                            MobclickAgent.onEvent(getActivity(), UmengEventId.HOME_MEDICINE_REMIND);
                            showShortToast("即将开放，敬请期待...");
                    }
                }
            });
            ArrayList<HomeItemBean> beanArrayList = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                HomeItemBean homeItemBean = new HomeItemBean();
                int drawableResId = R.drawable.ic_home_daily_record;
                int backgroundDrawableId = R.drawable.shape_home_label_daily_record;
                String title = null;

                switch (i) {
                    case 0:
                        backgroundDrawableId = R.drawable.shape_home_label_daily_record;
                        drawableResId = R.drawable.ic_home_daily_record;
                        title = "每日记录";
                        break;
                    case 1:
                        backgroundDrawableId = R.drawable.shape_home_label_medical_report;
                        drawableResId = R.drawable.ic_home_medical_record;
                        title = "就诊记录";
                        break;
                    case 2:
                        backgroundDrawableId = R.drawable.shape_home_label_medicine_remind;
                        drawableResId = R.drawable.ic_home_medicine_remind;
                        title = "用药提醒";
                        break;
                    case 3:
                        backgroundDrawableId = R.drawable.shape_home_label_health_report;
                        drawableResId = R.drawable.ic_home_health_report;
                        title = "健康报表";
                        break;
                }
                homeItemBean.setBackgroundDrawableId(backgroundDrawableId);
                homeItemBean.setDrawableResId(drawableResId);
                homeItemBean.setTitle(title);

                beanArrayList.add(homeItemBean);
            }
            homeRVAdapter.setDatas(beanArrayList);
        }
        mRecyclerViewHome.setAdapter(homeRVAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        topVPAdapter = null;
        homeRVAdapter.clear();
        homeRVAdapter = null;
    }

    @Override
    public void showHomeTopBanner(List<HomeBannerBean> homeBannerBeanList) {
        for (HomeBannerBean bean : homeBannerBeanList) {
            topVPAdapter.addFragment(HomeTopImageFragment.newInstance(bean.getImageUrl(), bean.getLinkUrl(), bean.getTitle()),bean.getTitle());
        }
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showNetworkError(String message) {

    }

    @Override
    public void showServerError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void hideLoading() {

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getResources().getString(R.string.umeng_page_title_home));
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getResources().getString(R.string.umeng_page_title_home));
    }
}
