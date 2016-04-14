package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.mvp.entity.request.GroupUpdateMemberBody;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/10.
 */
public interface IGroupRemoveDoctorPresenter extends IBasePresenter {
    void getCurrentMemberList(String doctorId, String groupId);

    void removeMembers(String doctorId, GroupUpdateMemberBody body);
}
