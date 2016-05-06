package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.module.medicineremind.MedicineBean;

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
        List<MedicineBean> beanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MedicineBean bean = new MedicineBean();
            bean.setCompanyName("XXX公司");
            bean.setFormOfDrug("片");
            bean.setMedicineName("药品" + i);
            bean.setSpecifications("24盒装");
            beanList.add(bean);
        }
        mViewListener.showMedicineList(beanList);
    }

    @Override
    public void getAllMedicines() {
        List<MedicineBean> beanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MedicineBean bean = new MedicineBean();
            bean.setCompanyName("XXX公司");
            bean.setFormOfDrug("片");
            bean.setMedicineName("药品" + i);
            bean.setSpecifications("24盒装");
            beanList.add(bean);
        }
        mViewListener.showMedicineList(beanList);
    }

    @Override
    public void getSearchHistory() {
        List<MedicineBean> beanList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            MedicineBean bean = new MedicineBean();
            bean.setCompanyName("XXX公司");
            bean.setFormOfDrug("颗");
            bean.setDose("" + (i + 1));
            bean.setMedicineName("药品" + i);
            bean.setSpecifications("24盒装");
            beanList.add(bean);
        }
        mViewListener.showSearchHistoryList(beanList);
    }
}
