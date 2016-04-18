package com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter;

import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateBasicInfoBody;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/7.
 */
public interface IGroupModifyPresenter extends IBasePresenter {
    void getGroupInfo(String doctorId, String groupId);

    void createGroup(String doctorId, GroupUpdateBasicInfoBody body);
}
