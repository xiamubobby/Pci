package com.wonders.xlab.pci.doctor.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/11.
 */
public interface IGroupPackageDetailModel extends IBaseModel {
    void getPackageDetail(String ownerId, String servicePackageId);
}
