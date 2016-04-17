package com.wonders.xlab.pci.doctor.data.presenter;

import com.wonders.xlab.pci.doctor.data.entity.request.GroupAuthorizeBody;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/10.
 */
public interface IGroupAuthPresenter extends IBasePresenter {
    void getGroupMemberList(String doctorId,String groupId);

    void authorize(String doctorId, String doctorGroupId, GroupAuthorizeBody body);
}