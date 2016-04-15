package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupAuthorizeBody;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/10.
 */
public interface IGroupAuthPresenter extends IBasePresenter {
    void getGroupMemberList(String doctorId,String groupId);

    void authorize(String doctorId, String doctorGroupId, GroupAuthorizeBody body);
}
