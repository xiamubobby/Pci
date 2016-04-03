package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.healthreport.bs.bean.BSBean;
import com.wonders.xlab.patient.mvp.entity.BloodSugarEntity;
import com.wonders.xlab.patient.mvp.model.IBloodSugarModel;
import com.wonders.xlab.patient.mvp.model.impl.BloodSugarModel;
import com.wonders.xlab.patient.mvp.presenter.IBloodSugarPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/4/1.
 */
public class BloodSugarPresenter extends BasePagePresenter implements IBloodSugarPresenter, BloodSugarModel.BloodSugarModelListener {
    private BloodSugarPresenterListener mListener;
    private IBloodSugarModel mIBloodSugarModel;

    public BloodSugarPresenter(BloodSugarPresenterListener listener) {
        mListener = listener;

        mIBloodSugarModel = new BloodSugarModel(this);
        addModel(mIBloodSugarModel);
    }


    @Override
    public void getBSList(String patientId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mListener.hideLoading();
            return;
        }
        mIBloodSugarModel.getBSList(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveBSSuccess(BloodSugarEntity bsEntity) {
        mListener.hideLoading();

        BloodSugarEntity.RetValuesEntity retValues = bsEntity.getRet_values();

        updatePageInfo(retValues.getNumber(), retValues.isFirst(), retValues.isLast());

        List<BloodSugarEntity.RetValuesEntity.ContentEntity> contentEntityList = retValues.getContent();

        if (null == contentEntityList) {
            mListener.showError("获取血糖数据失败，请重试！");
            return;
        }

        Collections.sort(contentEntityList, new Comparator<BloodSugarEntity.RetValuesEntity.ContentEntity>() {
            @Override
            public int compare(BloodSugarEntity.RetValuesEntity.ContentEntity lhs, BloodSugarEntity.RetValuesEntity.ContentEntity rhs) {
                long l = lhs.getRecordTime2Long();
                long r = rhs.getRecordTime2Long();
                return l < r ? 1 : (l == r ? 0 : -1);
            }
        });

        List<BSBean> bsBeanList = new ArrayList<>();
        for (int i = 0; i < contentEntityList.size(); i++) {
            BloodSugarEntity.RetValuesEntity.ContentEntity contentEntity = contentEntityList.get(i);

            BSBean bean = new BSBean();
            bean.setEarlyMorningBS(contentEntity.getBeforeDawn() + "");
            bean.setBreakfastBeforeBS(contentEntity.getBeforeBreakfast() + "");
            bean.setBreakfastAfterBS(contentEntity.getAfterBreakfast() + "");
            bean.setLunchBeforeBS(contentEntity.getBeforeLunch() + "");
            bean.setLunchAfterBS(contentEntity.getAfterLunch() + "");
            bean.setDinnerBeforeBS(contentEntity.getBeforeDinner() + "");
            bean.setDinnerAfterBS(contentEntity.getAfterDinner() + "");
            bean.setRandomBS(contentEntity.getRandomValue() + "");
            bean.setRecordTimeInMill(contentEntity.getRecordTime2Long());
            bsBeanList.add(bean);
        }

        if (shouldAppend()) {
            mListener.appendBloodPressureList(bsBeanList);
        } else {
            mListener.showBloodPressureList(bsBeanList);
        }
    }

    @Override
    public void onReceiveFailed(String message) {
        mListener.hideLoading();
        mListener.showError(message);
    }

    public interface BloodSugarPresenterListener extends BasePresenterListener {
        void showBloodPressureList(List<BSBean> bsBeanList);

        void appendBloodPressureList(List<BSBean> bsBeanList);
    }
}
