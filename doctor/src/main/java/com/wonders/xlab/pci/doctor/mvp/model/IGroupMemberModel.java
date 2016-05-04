package com.wonders.xlab.pci.doctor.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/13.
 */
public interface IGroupMemberModel extends IBaseModel {
    void getMemberList(String doctorId, String ownerId);
}
