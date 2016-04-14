package com.wonders.xlab.pci.doctor.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/13.
 */
public interface IGroupAuthMemberModel extends IBaseModel {
    void getAuthMemberList(String doctorId,String doctorGroupId);
}
