package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by jimmy on 16/5/3.
 */
public interface IMedicalRecordsPresenter extends IBasePresenter {

    void getMedicalRecordsList(String patientId, boolean isRefresh);
}