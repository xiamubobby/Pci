package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/5/6.
 */
public interface MedicineSearchPresenterContract {
    interface ViewListener extends BasePagePresenterListener {
        void showMedicineList(List<MedicineRealmBean> beanList);

        void appendMedicineList(List<MedicineRealmBean> beanList);

        void showSearchHistoryList(List<MedicineRealmBean> beanList);
    }

    interface Actions extends IBasePresenter {
        void search(String medicineName);

        void getAllMedicines();

        void getSearchHistory();

        /**
         * cache the search history
         * @param bean
         */
        void saveSearchHistory(MedicineRealmBean bean);

        void removeSearchHistoryById(String id);
    }
}
