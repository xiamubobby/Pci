package com.wonders.xlab.patient.module.main.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.main.home.adapter.HomeRVAdapter;
import com.wonders.xlab.patient.module.main.home.adapter.bean.HomeItemBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.view_pager_home)
    ViewPager mViewPagerHome;
    @Bind(R.id.recycler_view_home)
    RecyclerView mRecyclerViewHome;
    private HomeRVAdapter mHomeRVAdapter;
    private FragmentVPAdapter mTopVPAdapter;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewHome.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewHome.setItemAnimator(new DefaultItemAnimator());

        mTopVPAdapter = new FragmentVPAdapter(getActivity().getFragmentManager());

        setupBottomFunctionView();

    }

    private void setupBottomFunctionView() {
        mHomeRVAdapter = new HomeRVAdapter();
        ArrayList<HomeItemBean> beanArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HomeItemBean homeItemBean = new HomeItemBean();
            int drawableResId = R.drawable.ic_home_health_data;
            String title = null;
            switch (i) {
                case 0:
                    drawableResId = R.drawable.ic_home_health_data;
                    title = "每日记录";
                    break;
                case 1:
                    drawableResId = R.drawable.ic_home_health_data;
                    title = "就诊记录";
                    break;
                case 2:
                    drawableResId = R.drawable.ic_home_health_data;
                    title = "用药提醒";
                    break;
                case 3:
                    drawableResId = R.drawable.ic_home_health_data;
                    title = "数据报表";
                    break;
            }
            homeItemBean.setDrawableResId(drawableResId);
            homeItemBean.setTitle(title);

            beanArrayList.add(homeItemBean);
        }
        mHomeRVAdapter.setDatas(beanArrayList);
        mRecyclerViewHome.setAdapter(mHomeRVAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
