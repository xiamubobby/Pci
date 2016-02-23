package com.wonders.xlab.pci.doctor.mvp.presenter;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.doctor.module.bs.bean.BSBean;
import com.wonders.xlab.pci.doctor.mvp.entity.BSEntity;
import com.wonders.xlab.pci.doctor.mvp.model.BSModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IBSModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IBSPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public class BSPresenter extends BasePresenter implements IBSModel {
    private IBSPresenter mBloodPressurePresenter;
    private BSModel mBSModel;

    public BSPresenter(@NonNull IBSPresenter bloodPressurePresenter) {
        mBloodPressurePresenter = bloodPressurePresenter;

        mBSModel = new BSModel(this);
        addModel(mBSModel);
    }

    public void getBSList() {
//        mBSModel.getBSList();
        onReceiveBSSuccess(null);
    }

    @Override
    public void onReceiveBSSuccess(BSEntity BSEntity) {
        List<BSBean> BSBeanList = new ArrayList<>();

        long headerId = 0;
        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                headerId++;
            }
            BSBean bean = new BSBean();
            bean.setHeaderId(headerId);
            bean.setDiastolic("1" + i);
            bean.setSystolic("2" + i);
            bean.setHeartRate(i + "0");
            bean.setTime("2016-02-0" + i);
            BSBeanList.add(bean);
        }

        mBloodPressurePresenter.showBloodPressureList(BSBeanList);
    }

    @Override
    public void onReceiveFailed(String message) {

    }
}