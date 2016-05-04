package com.wonders.xlab.pci.doctor.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/18.
 */
public interface INotifiGroupInviteModel extends IBaseModel {
    void getGroupInviteNotifications(String doctorId, int page, int size);
}
