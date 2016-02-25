package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordBean;
import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordPhotoBean;
import com.wonders.xlab.pci.doctor.mvp.entity.MedicalRecordEntity;
import com.wonders.xlab.pci.doctor.mvp.model.MedicalRecordModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IMedicalRecordModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IMedicalRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/25.
 */
public class MedicalRecordPresenter extends BasePresenter implements IMedicalRecordModel{
    private IMedicalRecordPresenter mIMedicalRecordPresenter;
    private MedicalRecordModel mMedicalRecordModel;

    public MedicalRecordPresenter(IMedicalRecordPresenter iMedicalRecordPresenter) {
        mIMedicalRecordPresenter = iMedicalRecordPresenter;
        mMedicalRecordModel = new MedicalRecordModel(this);
        addModel(mMedicalRecordModel);
    }

    public void getMedicalRecordList() {
        onReceiveMedicalRecordSuccess(null);
//        mMedicalRecordModel.getMedicalRecordList();
    }


    @Override
    public void onReceiveMedicalRecordSuccess(MedicalRecordEntity entity) {
        final ArrayList<String> photos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            photos.add(Constant.DEFAULT_PORTRAIT);
        }

        List<MedicalRecordBean> beanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MedicalRecordPhotoBean bean = new MedicalRecordPhotoBean();
            bean.setTitle("心电图");
            bean.setTimeStr("2016-02-25");
            bean.setPhotos(photos);

            beanList.add(bean);
        }

        mIMedicalRecordPresenter.showMedicalRecordList(beanList);

    }

    @Override
    public void onReceiveFailed(String message) {

    }
}
