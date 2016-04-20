package com.wonders.xlab.pci.doctor.module.chatroom.bs.presenter.impl;

import android.support.annotation.NonNull;

import com.wonders.xlab.pci.doctor.data.entity.BSEntity;
import com.wonders.xlab.pci.doctor.data.model.impl.BSModel;
import com.wonders.xlab.pci.doctor.module.chatroom.bs.bean.BSBean;
import com.wonders.xlab.pci.doctor.module.chatroom.bs.presenter.IBSPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public class BSPresenter extends BasePagePresenter implements IBSPresenter, BSModel.BSModelListener {
    private BSPresenterListener mListener;
    private BSModel mBSModel;

    public BSPresenter(@NonNull BSPresenterListener listener) {
        mListener = listener;

        mBSModel = new BSModel(this);
        addModel(mBSModel);
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
        mBSModel.getBSList(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveBSSuccess(BSEntity bsEntity) {
        mListener.hideLoading();

        BSEntity.RetValuesEntity retValues = bsEntity.getRet_values();

        updatePageInfo(retValues.getNumber(), retValues.isFirst(), retValues.isLast());

        List<BSEntity.RetValuesEntity.ContentEntity> contentEntityList = retValues.getContent();

        if (null == contentEntityList) {
            mListener.showNetworkError("获取血糖数据失败，请重试！");
            return;
        }

        Collections.sort(contentEntityList, new Comparator<BSEntity.RetValuesEntity.ContentEntity>() {
            @Override
            public int compare(BSEntity.RetValuesEntity.ContentEntity lhs, BSEntity.RetValuesEntity.ContentEntity rhs) {
                long l = lhs.getRecordTime2Long();
                long r = rhs.getRecordTime2Long();
                return l < r ? 1 : (l == r ? 0 : -1);
            }
        });

        List<BSBean> bsBeanList = new ArrayList<>();
        for (int i = 0; i < contentEntityList.size(); i++) {
            BSEntity.RetValuesEntity.ContentEntity contentEntity = contentEntityList.get(i);

            BSBean bean = new BSBean();
            bean.setBreakfastBeforeBS(contentEntity.getBeforeBreakfast());
            bean.setBreakfastAfterBS(contentEntity.getAfterBreakfast());
            bean.setLunchBeforeBS(contentEntity.getBeforeLunch());
            bean.setLunchAfterBS(contentEntity.getAfterLunch());
            bean.setDinnerBeforeBS(contentEntity.getBeforeDinner());
            bean.setDinnerAfterBS(contentEntity.getAfterDinner());
            bean.setBeforeSleepBS(contentEntity.getBeforeDawn());
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
    public void onReceiveFailed(int code, String message) {
        mListener.showNetworkError(message);
    }

    public interface BSPresenterListener extends BasePagePresenterListener {
        void showBloodPressureList(List<BSBean> bsBeanList);

        void appendBloodPressureList(List<BSBean> bsBeanList);
    }
}