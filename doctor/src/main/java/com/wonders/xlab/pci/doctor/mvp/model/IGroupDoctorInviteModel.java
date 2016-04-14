package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupUpdateMemberBody;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/14.
 */
public interface IGroupDoctorInviteModel extends IBaseModel {
    void inviteDoctors(String doctorId, GroupUpdateMemberBody body);
}
