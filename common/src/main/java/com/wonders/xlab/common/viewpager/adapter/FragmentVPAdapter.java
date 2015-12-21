package com.wonders.xlab.common.viewpager.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class FragmentVPAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    public void clear() {
        if (mFragmentList != null) {
            mFragmentList.clear();
        }
        if (mTitleList != null) {
            mTitleList.clear();
        }

        notifyDataSetChanged();
    }

    public void setFragmentList(@NonNull List<Fragment> fragments) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        } else {
            mFragmentList.clear();
        }
        this.mFragmentList = fragments;
        notifyDataSetChanged();
    }

    public void setFragmentList(@NonNull List<Fragment> fragments,List<String> titleList) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        } else {
            mFragmentList.clear();
        }
        if (mTitleList == null) {
            mTitleList = new ArrayList<>();
        } else {
            mTitleList.clear();
        }
        this.mFragmentList.addAll(fragments);
        mTitleList.addAll(titleList);
        notifyDataSetChanged();
    }

    public void addFragment(@NonNull Fragment fragment) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        this.mFragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragment(@NonNull Fragment fragment, @NonNull String title) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        if (mTitleList == null) {
            mTitleList = new ArrayList<>();
        }
        this.mFragmentList.add(fragment);
        mTitleList.add(title);
        notifyDataSetChanged();
    }

    public FragmentVPAdapter(FragmentManager fm) {
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
        return mTitleList == null || mTitleList.size() <= 0 ? "" : mTitleList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
