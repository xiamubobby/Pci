package com.wonders.xlab.pci.doctor.mvp.presenter;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/8.
 */
public interface IGroupInviteDoctorPresenter extends IBasePresenter {
    void searchByNameOrTel(String doctorGroupId, String searchKey);
}
