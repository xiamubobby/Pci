package com.wonders.xlab.patient.module.dailyrecord.mvn.presenter;

import com.wonders.xlab.patient.module.dailyrecord.mvn.model.IdealRangeModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.model.impl.IIdealRangeModel;
import com.wonders.xlab.patient.module.dailyrecord.mvn.presenter.impl.IIdealRangePresenter;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public class IdealRangePresenter extends BasePresenter implements IIdealRangeModel {
    private IIdealRangePresenter mIIdealRangePresenter;

    private IdealRangeModel mIdealRangeModel;

    public IdealRangePresenter(IIdealRangePresenter iIdealRangePresenter) {
        mIIdealRangePresenter = iIdealRangePresenter;

        mIdealRangeModel = new IdealRangeModel(this);
        addModel(mIdealRangeModel);
    }

    /**
     * 获取血压理想范围
     *
     * @param userId
     */
    public void fetchIdealBPRange(String userId) {
        mIdealRangeModel.fetchIdealBPRange(userId);
    }

    /**
     * 获取血糖理想范围
     *
     * @param userId
     */
    public void fetchIdealBSRange(String userId) {
        mIdealRangeModel.fetchIdealBSRange(userId);
    }

    @Override
    public void onReceiveIdealRangeSuccess(String message) {
        mIIdealRangePresenter.hideLoading();
        mIIdealRangePresenter.showRange(message);
    }

    @Override
    public void onReceiveFailed(String message) {
        mIIdealRangePresenter.hideLoading();
        mIIdealRangePresenter.showError(message);
    }
}
