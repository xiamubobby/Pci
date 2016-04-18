package com.wonders.xlab.pci.doctor.module.me.groupmanage.packagemanage.presenter;

import com.wonders.xlab.pci.doctor.data.entity.request.GroupPackagePublishBody;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/10.
 */
public interface IGroupServiceModifyPresenter extends IBasePresenter {
    void getServicePackageInfo(String doctorGroupId, String servicePackageId);

    void publishPackage(GroupPackagePublishBody body);
}
