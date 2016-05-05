package com.wonders.xlab.patient.mvp.presenter.impl;


import com.wonders.xlab.patient.module.medicalpicture.bean.MedicalRecordBean;
import com.wonders.xlab.patient.module.medicalpicture.bean.MedicalRecordPhotoBean;
import com.wonders.xlab.patient.mvp.entity.MedicalRecordEntity;
import com.wonders.xlab.patient.mvp.model.impl.MedicalRecordModel;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/25.
 */
public class MedicalRecordPresenter extends BasePagePresenter implements MedicalRecordModel.MedicalRecordModelListener {
    private MedicalRecordPresenterListener mPresenterListener;
    private MedicalRecordModel mMedicalRecordModel;

    public MedicalRecordPresenter(MedicalRecordPresenterListener iMedicalRecordPresenter) {
        mPresenterListener = iMedicalRecordPresenter;
        mMedicalRecordModel = new MedicalRecordModel(this);
        addModel(mMedicalRecordModel);
    }

    public void getMedicalRecordList(String userId, boolean isRefresh) {
        if (isRefresh) {
            resetPageInfo();
        }
        if (mIsLast) {
            mPresenterListener.hideLoading();
            mPresenterListener.showReachTheLastPageNotice("");
            return;
        }
        mMedicalRecordModel.getMedicalRecordList(userId, getNextPageIndex(), DEFAULT_PAGE_SIZE);
    }

    @Override
    public void onReceiveMedicalRecordSuccess(MedicalRecordEntity entity) {
        mPresenterListener.hideLoading();

        MedicalRecordEntity.RetValuesEntity valuesEntity = entity.getRet_values();

        updatePageInfo(valuesEntity.getNumber(), valuesEntity.isFirst(), valuesEntity.isLast());

        List<MedicalRecordBean> beanList = new ArrayList<>();
        for (int i = 0; i < valuesEntity.getContent().size(); i++) {
            MedicalRecordEntity.RetValuesEntity.ContentEntity contentEntity = valuesEntity.getContent().get(i);

            List<MedicalRecordEntity.RetValuesEntity.ContentEntity.UserCasesEntity> userCasesEntityList = contentEntity.getUserCases();

            final ArrayList<String> photoThumbnails = new ArrayList<>();
            final ArrayList<String> photoOrigin = new ArrayList<>();
            for (int j = 0; j < userCasesEntityList.size(); j++) {
                photoThumbnails.add(userCasesEntityList.get(j).getMinCaseUrl());
                photoOrigin.add(userCasesEntityList.get(j).getCaseUrl());
            }

            MedicalRecordPhotoBean bean = new MedicalRecordPhotoBean();
            bean.setTitle(contentEntity.getTitle());
            bean.setTimeStr(DateUtil.format(contentEntity.getUploadTime(), "yyyy-MM-dd"));
            bean.setPhotoThumbnails(photoThumbnails);
            bean.setPhotosOrigin(photoOrigin);

            beanList.add(bean);
        }

        if (shouldAppend()) {
            mPresenterListener.appendMedicalRecordList(beanList);
        } else {
            mPresenterListener.showMedicalRecordList(beanList);
        }

    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mPresenterListener.hideLoading();
        mPresenterListener.showNetworkError(message);
    }

    @Override
    public void noMoreData(String message) {
        mPresenterListener.hideLoading();
    }

    public interface MedicalRecordPresenterListener extends BasePresenterListener {
        void showMedicalRecordList(List<MedicalRecordBean> beanList);

        void appendMedicalRecordList(List<MedicalRecordBean> beanList);

        void showReachTheLastPageNotice(String message);
    }
}
