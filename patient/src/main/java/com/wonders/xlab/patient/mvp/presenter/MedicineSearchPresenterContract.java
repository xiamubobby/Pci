package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.MedicineBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/5/6.
 */
public interface MedicineSearchPresenterContract {
    interface ViewListener extends BasePagePresenterListener {
        void showMedicineList(List<MedicineBean> beanList);

        void appendMedicineList(List<MedicineBean> beanList);

        void showSearchHistoryList(List<MedicineBean> beanList);
    }

    interface Actions extends IBasePresenter {
        void search(String medicineName);

        void getAllMedicines();

        void getSearchHistory();
    }
}
