package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/17.
 */
public interface INotifiPackageApplyPresenter extends IBasePresenter {
    void getPackageApplyNotifications(String doctorId);
}