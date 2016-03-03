package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.medicalrecord.bean.MedicalRecordBean;

import java.util.List;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 16/2/25.
 */
public interface IMedicalRecordPresenter extends IBasePresenter {
    void showMedicalRecordList(List<MedicalRecordBean> beanList);
}
