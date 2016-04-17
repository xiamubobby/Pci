package com.wonders.xlab.pci.doctor.data.presenter;

import com.wonders.xlab.pci.doctor.data.entity.request.GroupUpdateMemberBody;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/8.
 */
public interface IGroupInviteDoctorPresenter extends IBasePresenter {
    void searchByNameOrTel(String doctorId, String doctorGroupId, String searchKey);

    void inviteDoctors(String doctorId, GroupUpdateMemberBody body);
}
