package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupPackagePublishBody;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/10.
 */
public interface IGroupServiceModifyPresenter extends IBasePresenter {
    void getServicePackageInfo(String doctorGroupId, String servicePackageId);

    void publishPackage(GroupPackagePublishBody body);
}
