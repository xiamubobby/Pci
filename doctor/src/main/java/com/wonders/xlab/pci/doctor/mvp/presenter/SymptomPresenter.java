package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.symptom.bean.SymptomBean;
import com.wonders.xlab.pci.doctor.mvp.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.mvp.model.SymptomModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.ISymptomModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.ISymptomPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/24.
 */
public class SymptomPresenter extends BasePresenter implements ISymptomModel {
    private ISymptomPresenter mISymptomPresenter;
    private SymptomModel mSymptomModel;

    public SymptomPresenter(ISymptomPresenter ISymptomPresenter) {
        mISymptomPresenter = ISymptomPresenter;
        mSymptomModel = new SymptomModel(this);
        addModel(mSymptomModel);
    }

    public void getSymptomList() {
//        mSymptomModel.getSymptomList();
        onReceiveSymptomSuccess(null);
    }

    @Override
    public void onReceiveSymptomSuccess(SymptomEntity entity) {
        List<SymptomBean> symptomBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            SymptomBean bean = new SymptomBean();
            if (1 == i || 5 == i) {
                bean.setComment("comment" + i);
            }
            bean.setSymptomId("" + i);
            bean.setIsChecked(false);
            bean.setSymptomStr("胸闷，头疼，感冒");
            bean.setTimeStr("2016-02-25");

            symptomBeanList.add(bean);
        }

        mISymptomPresenter.showSymptomList(symptomBeanList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mISymptomPresenter.showError(message);
    }
}
