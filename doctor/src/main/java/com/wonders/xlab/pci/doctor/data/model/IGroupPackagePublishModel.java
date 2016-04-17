package com.wonders.xlab.pci.doctor.data.model;

import com.wonders.xlab.pci.doctor.data.entity.request.GroupPackagePublishBody;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/11.
 */
public interface IGroupPackagePublishModel extends IBaseModel {
    void publishPackage(GroupPackagePublishBody body);
}
