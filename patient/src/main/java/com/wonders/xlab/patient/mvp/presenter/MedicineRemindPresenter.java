package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.list.bean.MedicineRemindBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by WZH on 16/5/4.
 */
public class MedicineRemindPresenter extends BasePagePresenter implements MedicineRemindPresenterContract.Actions{

    private MedicineRemindPresenterContract.ViewListener mViewListener;

    @Inject
    public MedicineRemindPresenter(MedicineRemindPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Override
    public void getMedicineReminds() {
        List<MedicineRemindBean> medicineRemindBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MedicineRemindBean bean = new MedicineRemindBean();
            bean.amOrPmStr.set("上午");
            bean.timeInStr.set("09:30");
            bean.expiredDateInStr.set("2016-09-20");
            bean.medicineNameStr.set("阿司匹林，感冒灵颗粒,阿司匹林，感冒灵颗粒,阿司匹林，感冒灵颗粒,阿司匹林，感冒灵颗粒,");
            bean.isChecked.set(true);

            medicineRemindBeanList.add(bean);
        }

        mViewListener.showMedicineRemind(medicineRemindBeanList);
    }
}
