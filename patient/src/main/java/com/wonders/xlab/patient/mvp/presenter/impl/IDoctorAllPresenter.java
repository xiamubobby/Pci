package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.doctors.adapter.bean.AllDoctorItemBean;

import java.util.ArrayList;

/**
 * Created by hua on 16/3/14.
 */
public interface IDoctorAllPresenter {
    void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);

    void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);
}
