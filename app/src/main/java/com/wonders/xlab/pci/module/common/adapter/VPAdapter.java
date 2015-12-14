package com.wonders.xlab.pci.module.common.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class VPAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public void setFragmentList(@NonNull List<Fragment> fragments) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        } else {
            mFragmentList.clear();
        }
        this.mFragmentList = fragments;
        notifyDataSetChanged();
    }

    public void addFragment(@NonNull Fragment fragment) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        this.mFragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public VPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return (mFragmentList == null || mFragmentList.size() <= 0) ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
