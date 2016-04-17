package com.wonders.xlab.pci.doctor.data.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/13.
 */
public interface IGroupMemberModel extends IBaseModel {
    void getMemberList(String doctorId, String doctorGroupId);
}
