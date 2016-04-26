package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.common.manager.OttoManager;
import com.wonders.xlab.patient.assist.deviceconnection.entity.BSEntityList;
import com.wonders.xlab.patient.otto.BSSaveSuccessOtto;
import com.wonders.xlab.patient.data.entity.BSPeriodEntity;
import com.wonders.xlab.patient.mvp.model.IBSPeriodModel;
import com.wonders.xlab.patient.mvp.model.impl.BSMultiSaveModel;
import com.wonders.xlab.patient.mvp.model.impl.BSPeriodModel;
import com.wonders.xlab.patient.mvp.model.impl.BSSingleSaveModel;
import com.wonders.xlab.patient.mvp.presenter.IBSSavePresenter;

import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;


/**
 * Created by hua on 16/3/16.
 */
public class BSSavePresenter extends BasePresenter implements IBSSavePresenter, BSMultiSaveModel.BSSaveModelListener, BSPeriodModel.BSPeriodModelListener, BSSingleSaveModel.BSSaveModelListener {

    private BSSavePresenterListener mBSSavePresenterListener;

    private BSMultiSaveModel mBSMultiSaveModel;
    private BSSingleSaveModel mBSSingleSaveModel;
    private IBSPeriodModel mIBSPeriodModel;

    public BSSavePresenter(BSSavePresenterListener BSSavePresenterListener) {
        mBSSavePresenterListener = BSSavePresenterListener;
        mBSMultiSaveModel = new BSMultiSaveModel(this);
        mBSSingleSaveModel = new BSSingleSaveModel(this);
        mIBSPeriodModel = new BSPeriodModel(this);

        addModel(mBSSingleSaveModel);
        addModel(mBSMultiSaveModel);
        addModel(mIBSPeriodModel);
    }

    @Override
    public void getBSPeriodDic() {
        mIBSPeriodModel.getPeriodDic();
    }

    @Override
    public void saveBSSingle(String userId, long date, int timeIndex, float bloodSugarValue) {
        mBSSingleSaveModel.saveBSSingle(userId, date, timeIndex, bloodSugarValue);

    }

    @Override
    public void saveBS(String userId, BSEntityList bsEntityList) {
        mBSMultiSaveModel.saveBS(userId, bsEntityList);
    }

    @Override
    public void onSaveMultiBSSuccess(String message) {

        mBSSavePresenterListener.hideLoading();
        mBSSavePresenterListener.onSaveBSSuccess(message);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mBSSavePresenterListener.hideLoading();
        mBSSavePresenterListener.showNetworkError(message);
    }

    @Override
    public void onReceiveBSPeriodSuccess(BSPeriodEntity.RetValuesEntity valuesEntity) {
        int currentPeriodIndex = valuesEntity.getCurrentTime() - 1;
        if (currentPeriodIndex < 0) {
            currentPeriodIndex = 0;
        }
        mBSSavePresenterListener.showBSPeriodDicList(valuesEntity.getTimeString(), currentPeriodIndex);
    }

    @Override
    public void onSaveSingleBSSuccess(String message) {
        OttoManager.post(new BSSaveSuccessOtto());

        mBSSavePresenterListener.hideLoading();
        mBSSavePresenterListener.onSaveBSSuccess(message);
    }

    public interface BSSavePresenterListener extends BasePresenterListener {
        void onSaveBSSuccess(String message);

        void showBSPeriodDicList(List<String> periodList, int currentPeriodIndex);
    }
}
