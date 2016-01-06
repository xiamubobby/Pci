package com.wonders.xlab.pci.module.task.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.wonders.xlab.common.application.OttoManager;
import com.wonders.xlab.pci.module.task.MedicineRecordFragment;
import com.wonders.xlab.pci.module.task.bean.MedicineRecordBean;
import com.wonders.xlab.pci.module.task.otto.CanModifyMedicineOtto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/28.
 */
public class MedicineVPAdapter extends FragmentPagerAdapter {
    private List<MedicineRecordBean> morningMedicine;
    private List<MedicineRecordBean> noonMedicine;
    private List<MedicineRecordBean> nightMedicine;
    private boolean canModify;

    public MedicineVPAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDatas(List<MedicineRecordBean> morningMedicine, List<MedicineRecordBean> noonMedicine, List<MedicineRecordBean> nightMedicine, boolean canModify) {
        if (this.morningMedicine == null) {
            this.morningMedicine = new ArrayList<>();
        } else {
            this.morningMedicine.clear();
        }
        if (this.noonMedicine == null) {
            this.noonMedicine = new ArrayList<>();
        } else {
            this.noonMedicine.clear();
        }
        if (this.nightMedicine == null) {
            this.nightMedicine = new ArrayList<>();
        } else {
            this.nightMedicine.clear();
        }

        this.morningMedicine.addAll(morningMedicine);
        this.noonMedicine.addAll(noonMedicine);
        this.nightMedicine.addAll(nightMedicine);
        this.canModify = canModify;

        OttoManager.post(new CanModifyMedicineOtto(canModify));
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = MedicineRecordFragment.newInstance(morningMedicine, canModify);
                break;
            case 1:
                fragment = MedicineRecordFragment.newInstance(noonMedicine, canModify);
                break;
            case 2:
                fragment = MedicineRecordFragment.newInstance(nightMedicine, canModify);
                break;
            default:
                fragment = MedicineRecordFragment.newInstance(morningMedicine, canModify);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "早";
        switch (position) {
            case 0:
                title = "早";
                break;
            case 1:
                title = "午";
                break;
            case 2:
                title = "晚";
                break;
        }
        return title;
    }
}
