package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/7.
 */
public interface IGroupModifyPresenter extends IBasePresenter {
    void getGroupInfo(String doctorId, String groupId);
}
