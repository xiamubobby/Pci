package com.wonders.xlab.pci.doctor.module.me.groupmanage.presenter;

import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateMemberBody;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/10.
 */
public interface IGroupRemoveDoctorPresenter extends IBasePresenter {
    void getCurrentMemberList(String doctorId, String ownerId);

    void removeMembers(String doctorId, GroupUpdateMemberBody body);
}
