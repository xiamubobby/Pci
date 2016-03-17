package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.mvp.model.impl.IdealRangeModel;
import com.wonders.xlab.patient.mvp.presenter.IIdealRangePresenter;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/16.
 */
public class IdealRangePresenter extends BasePresenter implements IIdealRangePresenter,IdealRangeModel.IdealRangeModelListener {
    private IdealRangePresenterListener mIdealRangePresenterListener;

    private IdealRangeModel mIdealRangeModel;

    public IdealRangePresenter(IdealRangePresenterListener idealRangePresenterListener) {
        mIdealRangePresenterListener = idealRangePresenterListener;

        mIdealRangeModel = new IdealRangeModel(this);
        addModel(mIdealRangeModel);
    }

    @Override
    public void fetchIdealBPRange(String userId) {
        mIdealRangeModel.fetchIdealBPRange(userId);
    }

    @Override
    public void fetchIdealBSRange(String userId) {
        mIdealRangeModel.fetchIdealBSRange(userId);
    }

    @Override
    public void onReceiveIdealRangeSuccess(String message) {
        mIdealRangePresenterListener.hideLoading();
        mIdealRangePresenterListener.showRange(message);
    }

    @Override
    public void onReceiveFailed(String message) {
        mIdealRangePresenterListener.hideLoading();
        mIdealRangePresenterListener.showError(message);
    }

    public interface IdealRangePresenterListener extends BasePresenterListener {
        void showRange(String range);
    }
}
