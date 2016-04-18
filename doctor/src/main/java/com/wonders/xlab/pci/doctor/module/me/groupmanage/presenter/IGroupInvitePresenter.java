package com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/14.
 */
public interface IGroupInvitePresenter extends IBasePresenter{
    void getInviteNotification(String doctorId);
}
