package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailPackageBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.impl.IBasePresenter;

/**
 * Created by hua on 16/3/16.
 */
public interface IDoctorDetailPresenter extends IBasePresenter {
    void showBasicInfo();

    void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

    void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList);

    void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);
}
