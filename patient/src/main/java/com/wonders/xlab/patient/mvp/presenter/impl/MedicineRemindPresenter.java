package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.main.home.medicineremind.bean.MedicineRemindBean;
import com.wonders.xlab.patient.module.main.home.medicineremind.bean.MedicineRemindDataBean;
import com.wonders.xlab.patient.mvp.entity.MedicineRemindEntity;
import com.wonders.xlab.patient.mvp.model.impl.MedicineRemindModel;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by WZH on 16/5/4.
 */
public class MedicineRemindPresenter extends BasePagePresenter implements MedicineRemindModel.MedicineRemindModelListener {

    private MedicineRemindPresenterListener mPresenterListener;
    private MedicineRemindModel mMedicineRemindModel;

    public MedicineRemindPresenter(MedicineRemindPresenterListener iMedicineRemindPresenter) {
        mPresenterListener = iMedicineRemindPresenter;
        mMedicineRemindModel = new MedicineRemindModel(this);
        addModel(mMedicineRemindModel);
    }

    public void getMedicineRemindList(String userId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }

        if (mIsLast) {
            mPresenterListener.hideLoading();
            mPresenterListener.showReachTheLastPageNotice("");
            return;
        }
        mMedicineRemindModel.getMedicalRecordList(userId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveMedicalRecordSuccess(MedicineRemindEntity entity) {
        mPresenterListener.hideLoading();
        MedicineRemindEntity.RetValuesEntity valuesEntity = entity.getRet_values();

        List<MedicineRemindBean> beanList = new ArrayList<>();

        for (int i = 0; i < valuesEntity.getContent().size(); i++) {
            MedicineRemindEntity.RetValuesEntity.ContentEntity contentEntity = valuesEntity.getContent().get(i);
            MedicineRemindDataBean bean = new MedicineRemindDataBean();
            beanList.add(bean);
        }
        if (shouldAppend()) {
            mPresenterListener.appendMedicineRemindList(beanList);
        } else {
            mPresenterListener.showMedicineRemindList(beanList);
        }
    }

    @Override
    public void noMoreData(String message) {
        mPresenterListener.hideLoading();
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mPresenterListener.hideLoading();
        mPresenterListener.showNetworkError(message);
    }

    public interface MedicineRemindPresenterListener extends BasePresenterListener {
        void showMedicineRemindList(List<MedicineRemindBean> beanList);

        void appendMedicineRemindList(List<MedicineRemindBean> beanList);

        void showReachTheLastPageNotice(String message);
    }
}
