package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/14.
 */
public interface INotifiGroupInvitePresenter extends IBasePresenter {
    void getInviteNotifications(String doctorId);
}
