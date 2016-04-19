package com.wonders.xlab.pci.doctor.module.me.notification.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/14.
 */
public interface INotifiGroupInvitePresenter extends IBasePresenter {
    void getInviteNotifications(String doctorId);

    void agreeOrRejectJoinDoctorGroup(String doctorId, String ownerId, boolean isToAgree);
}
