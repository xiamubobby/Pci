package com.wonders.xlab.pci.doctor.mvp.presenter;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.doctor.module.bp.bean.BPBean;
import com.wonders.xlab.pci.doctor.mvp.entity.BPEntity;
import com.wonders.xlab.pci.doctor.mvp.model.BPModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IBPModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IBPPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public class BPPresenter extends BasePresenter implements IBPModel {
    private IBPPresenter mBloodPressurePresenter;
    private BPModel mBPModel;

    public BPPresenter(@NonNull IBPPresenter bloodPressurePresenter) {
        mBloodPressurePresenter = bloodPressurePresenter;

        mBPModel = new BPModel(this);
        addModel(mBPModel);
    }

    public void getBPList() {
//        mBPModel.getBPList();
        onReceiveBPSuccess(null);
    }

    @Override
    public void onReceiveBPSuccess(BPEntity bpEntity) {
        List<BPBean> bpBeanList = new ArrayList<>();

        long headerId = 0;
        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                headerId++;
            }
            BPBean bean = new BPBean();
            bean.setHeaderId(headerId);
            bean.setDiastolic("1" + i);
            bean.setSystolic("2" + i);
            bean.setHeartRate(i + "0");
            bean.setTime("2016-02-0" + i);
            bpBeanList.add(bean);
        }

        mBloodPressurePresenter.showBloodPressureList(bpBeanList);
    }

    @Override
    public void onReceiveFailed(String message) {

    }
}