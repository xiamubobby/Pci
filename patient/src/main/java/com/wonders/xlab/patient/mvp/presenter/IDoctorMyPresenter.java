package com.wonders.xlab.patient.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/3/17.
 */
public interface IDoctorMyPresenter extends IBasePresenter {
    void getMyDoctors(String patientId);
}
