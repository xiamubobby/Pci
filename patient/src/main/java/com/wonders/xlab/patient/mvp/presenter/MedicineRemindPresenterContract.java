package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.bean.MedicineRemindBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/5/5.
 */
public interface MedicineRemindPresenterContract {
    interface ViewListener extends BasePagePresenterListener{
        void showMedicineRemind(List<MedicineRemindBean> medicineRemindBeanList);
    }

    interface Actions extends IBasePresenter{
        void getMedicineReminds();
    }
}
