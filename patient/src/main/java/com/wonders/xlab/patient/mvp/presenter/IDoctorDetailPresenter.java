package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface IDoctorDetailPresenter extends IBasePresenter {
    void fetchDoctorDetailInfo(String patientId,String doctorId);

    void orderPackage(String patientId, String packageId, String paymentChannel);
}
