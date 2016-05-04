package com.wonders.xlab.pci.doctor.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/19.
 */
public interface IAgreeJoinDoctorGroupModel extends IBaseModel {
    void agreeOrRejectJoinDoctorGroup(String doctorId, String ownerId, String inviteStatus);
}
