package com.wonders.xlab.patient.module.doctors;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.squareup.otto.Subscribe;
import com.wonders.xlab.common.flyco.TabEntity;
import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.common.viewpager.adapter.FragmentVPAdapter;
import com.wonders.xlab.patient.R;
import com.wonders.xlab.patient.module.alldoctor.AllDoctorFragment;
import com.wonders.xlab.patient.module.mydoctor.DoctorMyFragment;
import com.wonders.xlab.patient.otto.DoctorTabChangeOtto;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.hua.library.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends BaseFragment {


    @Bind(R.id.tab_doctors)
    CommonTabLayout mTab;
    @Bind(R.id.view_pager_doctors)
    ViewPager mViewPager;

    public DoctorFragment() {
        // Required empty public constructor
    }

    public static DoctorFragment getInstance() {
        return new DoctorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OttoManager.register(this);
        View view = inflater.inflate(R.layout.doctors_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentVPAdapter fragmentVPAdapter = new FragmentVPAdapter(getActivity().getFragmentManager());
        fragmentVPAdapter.addFragment(DoctorMyFragment.newInstance());
        fragmentVPAdapter.addFragment(AllDoctorFragment.newInstance());
        mViewPager.setAdapter(fragmentVPAdapter);

        setupTopTab();
    }

    private void setupTopTab() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            switch (i) {
                case 0:
                    tabEntities.add(new TabEntity("我的医生", R.drawable.tab_home_select, R.drawable.tab_home_unselect));
                    break;
                case 1:
                    tabEntities.add(new TabEntity("所有医生", R.drawable.tab_contact_select, R.drawable.tab_contact_unselect));
                    break;
            }
        }
        mTab.setTabData(tabEntities);
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Subscribe
    public void changeTabPostion(DoctorTabChangeOtto otto) {
        mViewPager.setCurrentItem(otto.getPosition(),true);
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OttoManager.unregister(this);
        ButterKnife.unbind(this);
    }
}