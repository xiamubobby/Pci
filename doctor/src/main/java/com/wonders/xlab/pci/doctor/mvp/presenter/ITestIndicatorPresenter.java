package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by jimmy on 16/4/27.
 */
public interface ITestIndicatorPresenter extends IBasePresenter {

    void getTestIndicatorList(String patientId, String doctorId);
}
