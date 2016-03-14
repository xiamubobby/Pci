package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.main.doctors.adapter.bean.MyDoctorItemBean;

import java.util.ArrayList;

/**
 * Created by hua on 16/3/14.
 */
public interface IMyDoctorPresenter {
    void showMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);

    void appendMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);
}
