package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.me.groupmanage.adapter.bean.GroupDoctorBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/4/10.
 */
public interface IGroupRemoveDoctorPresenter extends IBasePresenter {
    void getCurrentMemberList(String groupId);

    void removeMembers(List<GroupDoctorBean> doctorBeanList);
}
