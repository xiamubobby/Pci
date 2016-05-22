package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface IDoctorGroupDetailPresenter extends IBasePresenter {
    void fetchDoctorGroupDetailInfo(String patientId,String ownerId);

    void orderPackage(String patientId, String packageId, String paymentChannel);
}
