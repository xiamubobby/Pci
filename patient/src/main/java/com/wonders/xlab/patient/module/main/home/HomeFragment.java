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
import im.hua.uikit.viewpager.CirclePageIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.view_pager_home)
    ViewPager mViewPagerHome;
    @Bind(R.id.recycler_view_home)
    RecyclerView mRecyclerViewHome;
    @Bind(R.id.indicator_home)
    CirclePageIndicator mIndicatorHome;
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
        mTopVPAdapter.addFragment(HomeTopCircleFragment.newInstance(null));
        mTopVPAdapter.addFragment(HomeTopImageFragment.newInstance("http://mmbiz.qpic.cn/mmbiz/gj62mn6HWrkADfXmMmNEmic7BRTB30aZicwJ74R0ibLvAXco4dLTJtOAkAGRW4PklnevKvqJkNzmGtosU9HUbFQXQ/640?wx_fmt=jpeg&tp=webp&wxfrom=5", "https://mp.weixin.qq.com/s?__biz=MzI3MTE0MzE4OA==&mid=402506650&idx=1&sn=835501fbff82d74eeb091705bad45908&scene=1&srcid=0313puhCxxMzCRem3xajbdJu&key=710a5d99946419d9a9ec354ed7c1757fbfb4f58a2db84028d9e611b33539e3f43e16d94e206510518e370b3bd8940838&ascene=0&uin=MjgwMjI2Mzc1&devicetype=iMac+MacBookPro11%2C5+OSX+OSX+10.11.2+build(15C50)&version=11020201&pass_ticket=CxvDr1ZlxeWQYh7f29QnAfCynylFVU0mB6pChMo2RpBt24d5WMM7S5RlQfwO1Ubj"));
        mTopVPAdapter.addFragment(HomeTopImageFragment.newInstance("http://mmbiz.qpic.cn/mmbiz/gj62mn6HWrmN80xGVWnvR6bTDzVHopNpt2bVa3U6DgA29wkia6WaMZXDomeib22BjpIRpD3AhdsJev9j50ia8FKpg/0?tp=webp&wxfrom=5&wx_lazy=1", "https://mp.weixin.qq.com/s?__biz=MzI3MTE0MzE4OA==&mid=403001788&idx=1&sn=2840b64b96e3ed32f35d4194b75f3aaa&scene=1&srcid=0313W1BPh2zSVgeHLN9KoOJ8&key=710a5d99946419d900fad65bd3cbdf05f4f85373e58259a5d49ccd4133930038e475e17397a142ffd6c3466ccc1b8191&ascene=0&uin=MjgwMjI2Mzc1&devicetype=iMac+MacBookPro11%2C5+OSX+OSX+10.11.2+build(15C50)&version=11020201&pass_ticket=CxvDr1ZlxeWQYh7f29QnAfCynylFVU0mB6pChMo2RpBt24d5WMM7S5RlQfwO1Ubj"));
        mViewPagerHome.setAdapter(mTopVPAdapter);

        mIndicatorHome.setViewPager(mViewPagerHome);

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
