package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.edit.adapter.MedicineRemindEditBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineRemindEditPresenter extends BasePresenter implements MedicineRemindEditPresenterContract.Actions {
    private MedicineRemindEditPresenterContract.ViewListener mViewListener;

    @Inject
    public MedicineRemindEditPresenter(MedicineRemindEditPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Override
    public void getMedicineRemindInfoById(String medicineRemindId) {

        List<MedicineRemindEditBean> beanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MedicineRemindEditBean bean = new MedicineRemindEditBean();
            bean.setName("阿司匹林");
            bean.setNumber("3");
            bean.setUnit("粒");
            beanList.add(bean);
        }
        mViewListener.showMedicineRemindInfo(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE) , Calendar.getInstance().getTimeInMillis(), null, "该吃药了", beanList);
    }
}
