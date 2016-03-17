package com.wonders.xlab.patient.mvp.presenter.listener;

import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupMemberBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailGroupOfDoctorBean;
import com.wonders.xlab.patient.module.doctors.detail.adapter.bean.DoctorDetailPackageBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/16.
 */
public interface DoctorDetailPresenterListener extends BasePresenterListener {
    void showBasicInfo();

    void showPackageList(ArrayList<DoctorDetailPackageBean> packageList);

    void showGroupMemberList(ArrayList<DoctorDetailGroupMemberBean> groupMemberList);

    void showGroupOfDoctorList(ArrayList<DoctorDetailGroupOfDoctorBean> groupOfDoctorList);
}
