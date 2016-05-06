package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.edit.adapter.MedicineRemindEditBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/5/6.
 */
public interface MedicineRemindEditPresenterContract {
    interface ViewListener extends BasePresenterListener {
        void showMedicineRemindInfo(int hour, int minutes, long startDate, Long endDate, String message, List<MedicineRemindEditBean> beanList);
    }

    interface Actions extends IBasePresenter {
        void getMedicineRemindInfoById(String medicineRemindId);
    }
}
