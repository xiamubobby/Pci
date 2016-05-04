package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupUpdateBasicInfoBody;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/12.
 */
public interface IGroupCreateModel extends IBaseModel {
    void createGroup(String doctorId, GroupUpdateBasicInfoBody body);
}
