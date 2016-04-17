package com.wonders.xlab.pci.doctor.data.presenter.impl;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.chatroom.bs.bean.BSBean;
import com.wonders.xlab.pci.doctor.data.entity.BSEntity;
import com.wonders.xlab.pci.doctor.data.model.impl.BSModel;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/22.
 */
public class BSPresenter extends BasePresenter implements BSModel.BSModelListener {
    private BSPresenterListener mBloodPressurePresenter;
    private BSModel mBSModel;

    public BSPresenter(@NonNull BSPresenterListener bloodPressurePresenter) {
        mBloodPressurePresenter = bloodPressurePresenter;

        mBSModel = new BSModel(this);
        addModel(mBSModel);
    }

    public void getBSList(String userId) {
        mBSModel.getBSList(userId);
    }

    @Override
    public void onReceiveBSSuccess(BSEntity bsEntity) {
        List<BSEntity.RetValuesEntity.ContentEntity> contentEntityList = bsEntity.getRet_values().getContent();

        if (null == contentEntityList) {
            mBloodPressurePresenter.showNetworkError(Constant.ERROR_MESSAGE);
            return;
        }

        long headerId = 0;
        List<BSBean> bsBeanList = new ArrayList<>();
        for (int i = 0; i < contentEntityList.size(); i++) {
            BSEntity.RetValuesEntity.ContentEntity contentEntity = contentEntityList.get(i);

            if (0 != i) {
                if (!DateUtil.isTheSameMonth(contentEntityList.get(i - 1).getRecordTime2Long(), contentEntity.getRecordTime2Long())) {
                    headerId++;
                }

            }

            BSBean bean = new BSBean();
            bean.setHeaderId(headerId);
            bean.setEarlyMorningBS(contentEntity.getBeforeDawn() + "");
            bean.setBreakfastBeforeBS(contentEntity.getBeforeBreakfast() + "");
            bean.setBreakfastAfterBS(contentEntity.getAfterBreakfast() + "");
            bean.setLunchBeforeBS(contentEntity.getBeforeLunch() + "");
            bean.setLunchAfterBS(contentEntity.getAfterLunch() + "");
            bean.setDinnerBeforeBS(contentEntity.getBeforeDinner() + "");
            bean.setDinnerAfterBS(contentEntity.getAfterDinner() + "");
            bean.setRandomBS(contentEntity.getRandomValue() + "");
            bean.setHeaderTime(DateUtil.format(contentEntity.getRecordTime2Long(), "yyyy-MM-dd"));
            bsBeanList.add(bean);
        }

        mBloodPressurePresenter.showBloodPressureList(bsBeanList);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mBloodPressurePresenter.showNetworkError(message);
    }

    public interface BSPresenterListener extends BasePresenterListener {
        void showBloodPressureList(List<BSBean> bsBeanList);
    }
}