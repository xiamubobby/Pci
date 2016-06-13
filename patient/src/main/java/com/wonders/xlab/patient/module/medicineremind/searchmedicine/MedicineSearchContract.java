package com.wonders.xlab.patient.module.medicineremind.searchmedicine;

import com.wonders.xlab.patient.module.medicineremind.MedicineRealmBean;
import com.wonders.xlab.patient.mvp.entity.MedicineListEntity;

import java.util.List;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BaseModelListener;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by WZH on 16/6/13.
 */
public interface MedicineSearchContract {
    interface Callback extends BaseModelListener {
        void onReceiveMedicinesSuccess(MedicineListEntity entity);
    }

    interface Model extends IBaseModel {
        void search(String searchKey, Callback callback);

        void getAllMedicines(Callback callback);
    }

    interface ViewListener extends BasePagePresenterListener {
        void showMedicineList(List<MedicineRealmBean> beanList, String[] sections);

        void appendMedicineList(List<MedicineRealmBean> beanList);

        void showSearchHistoryList(List<MedicineRealmBean> beanList);
    }

    interface Presenter extends IBasePresenter {
        void search(String medicineName);

        void getAllMedicines();

        void getSearchHistory();

        /**
         * cache the search history
         *
         * @param bean
         */
        void saveSearchHistory(MedicineRealmBean bean);

        void removeSearchHistoryById(String id);
    }
}
