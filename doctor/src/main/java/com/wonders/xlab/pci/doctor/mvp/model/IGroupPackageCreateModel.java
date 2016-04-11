package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.GroupPackageCreateBody;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/11.
 */
public interface IGroupPackageCreateModel extends IBaseModel {
    void createPackage(GroupPackageCreateBody body);
}
