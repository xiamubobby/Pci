package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.bean.MedicineSearchAllBean;
import com.wonders.xlab.patient.module.medicineremind.searchmedicine.adapter.bean.MedicineSearchHistoryBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by hua on 16/5/6.
 */
public class MedicineSearchPresenter extends BasePagePresenter implements MedicineSearchPresenterContract.Actions {
    private MedicineSearchPresenterContract.ViewListener mViewListener;

    @Inject
    public MedicineSearchPresenter(MedicineSearchPresenterContract.ViewListener viewListener) {
        mViewListener = viewListener;
    }

    @Override
    public void search(String medicineName) {
        List<MedicineSearchAllBean> beanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MedicineSearchAllBean bean = new MedicineSearchAllBean();
            bean.setCompanyName("XXX公司");
            bean.setFormOfDrug("片");
            bean.setMedicineName("药品"+i);
            bean.setSpecifications("24盒装");
            beanList.add(bean);
        }
        mViewListener.showMedicineList(beanList);
    }

    @Override
    public void getAllMedicines() {
        List<MedicineSearchAllBean> beanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MedicineSearchAllBean bean = new MedicineSearchAllBean();
            bean.setCompanyName("XXX公司");
            bean.setFormOfDrug("片");
            bean.setMedicineName("药品"+i);
            bean.setSpecifications("24盒装");
            beanList.add(bean);
        }
        mViewListener.showMedicineList(beanList);
    }

    @Override
    public void getSearchHistory() {
        List<MedicineSearchHistoryBean> beanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MedicineSearchHistoryBean bean = new MedicineSearchHistoryBean();
            bean.setMedicineName("药品"+i);
            beanList.add(bean);
        }
        mViewListener.showSearchHistoryList(beanList);
    }
}
