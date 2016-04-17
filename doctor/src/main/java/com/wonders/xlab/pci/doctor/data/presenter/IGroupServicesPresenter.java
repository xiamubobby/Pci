package com.wonders.xlab.pci.doctor.data.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/10.
 */
public interface IGroupServicesPresenter extends IBasePresenter {
    void getPackages(String groupId);
}
