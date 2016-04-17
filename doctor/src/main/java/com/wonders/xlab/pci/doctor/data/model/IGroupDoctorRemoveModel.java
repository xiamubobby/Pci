package com.wonders.xlab.pci.doctor.data.model;

import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateMemberBody;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/14.
 */
public interface IGroupDoctorRemoveModel extends IBaseModel {
    void removeDoctors(String doctorId, GroupUpdateMemberBody body);
}
