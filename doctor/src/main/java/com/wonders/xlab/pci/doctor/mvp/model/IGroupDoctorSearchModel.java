package com.wonders.xlab.pci.doctor.mvp.model;

import im.hua.library.base.mvp.IBaseModel;

/**
 * Created by hua on 16/4/12.
 */
public interface IGroupDoctorSearchModel extends IBaseModel {
    void searchDoctorByTelOrName(String doctorId, String doctorGroupId, String tel, String name);
}
