package com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/7.
 */
public interface IGroupListPresenter extends IBasePresenter {
    void getGroupList(boolean isRefresh, String doctorId);
}
