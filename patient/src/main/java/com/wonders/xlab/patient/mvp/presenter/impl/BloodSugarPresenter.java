package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.healthreport.bs.bean.BSBean;
import com.wonders.xlab.patient.mvp.entity.BloodSugarEntity;
import com.wonders.xlab.patient.mvp.model.IBloodSugarModel;
import com.wonders.xlab.patient.mvp.model.impl.BloodSugarModel;
import com.wonders.xlab.patient.mvp.presenter.IBloodSugarPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;


/**
 * Created by hua on 16/4/1.
 */
public class BloodSugarPresenter extends BasePresenter implements IBloodSugarPresenter, BloodSugarModel.BloodSugarModelListener {
    private BloodSugarPresenterListener mBloodSugarPresenterListener;
    private IBloodSugarModel mIBloodSugarModel;

    public BloodSugarPresenter(BloodSugarPresenterListener bloodSugarPresenterListener) {
        mBloodSugarPresenterListener = bloodSugarPresenterListener;

        mIBloodSugarModel = new BloodSugarModel(this);
        addModel(mIBloodSugarModel);
    }


    @Override
    public void getBSList(String userId) {
        mIBloodSugarModel.getBSList(userId);
    }

    @Override
    public void onReceiveBSSuccess(BloodSugarEntity bsEntity) {
        List<BloodSugarEntity.RetValuesEntity.ContentEntity> contentEntityList = bsEntity.getRet_values().getContent();

        if (null == contentEntityList) {
            mBloodSugarPresenterListener.showError("获取血糖数据失败，请重试！");
            return;
        }

        long headerId = 0;
        List<BSBean> bsBeanList = new ArrayList<>();
        for (int i = 0; i < contentEntityList.size(); i++) {
            BloodSugarEntity.RetValuesEntity.ContentEntity contentEntity = contentEntityList.get(i);

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

        mBloodSugarPresenterListener.showBloodPressureList(bsBeanList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mBloodSugarPresenterListener.showError(message);
    }

    public interface BloodSugarPresenterListener extends BasePresenterListener{
        void showBloodPressureList(List<BSBean> bsBeanList);
    }
}
