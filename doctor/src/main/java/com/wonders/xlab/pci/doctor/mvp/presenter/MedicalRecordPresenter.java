package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordPhotoBean;
import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;
import com.wonders.xlab.pci.doctor.mvp.model.MedicalRecordModel;
import com.wonders.xlab.pci.doctor.mvp.model.listener.MedicalRecordModelListener;
import com.wonders.xlab.pci.doctor.mvp.presenter.listener.MedicalRecordPresenterListener;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.utils.DateUtil;

/**
 * Created by hua on 16/2/25.
 */
public class MedicalRecordPresenter extends BasePresenter implements MedicalRecordModelListener {
    private MedicalRecordPresenterListener mIMedicalRecordPresenter;
    private MedicalRecordModel mMedicalRecordModel;

    public MedicalRecordPresenter(MedicalRecordPresenterListener iMedicalRecordPresenter) {
        mIMedicalRecordPresenter = iMedicalRecordPresenter;
        mMedicalRecordModel = new MedicalRecordModel(this);
        addModel(mMedicalRecordModel);
    }

    public void getMedicalRecordList(String userId) {
        mMedicalRecordModel.getMedicalRecordList(userId);
    }

    @Override
    public void onReceiveMedicalRecordSuccess(MedicalRecordEntity entity) {
        MedicalRecordEntity.RetValuesEntity valuesEntity = entity.getRet_values();
        if (null == valuesEntity) {
            mIMedicalRecordPresenter.showError("获取数据失败，请重试！");
            return;
        }

        List<MedicalRecordBean> beanList = new ArrayList<>();
        for (int i = 0; i < entity.getRet_values().getContent().size(); i++) {
            MedicalRecordEntity.RetValuesEntity.ContentEntity contentEntity = entity.getRet_values().getContent().get(i);

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

        mIMedicalRecordPresenter.showMedicalRecordList(beanList);

    }

    @Override
    public void onReceiveFailed(String message) {
        mIMedicalRecordPresenter.showError(message);
    }

    @Override
    public void noMoreData(String message) {
        mIMedicalRecordPresenter.hideLoading();
    }
}
