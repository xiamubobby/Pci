package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.module.main.doctors.adapter.bean.AllDoctorItemBean;

import java.util.ArrayList;

/**
 * Created by hua on 16/3/14.
 */
public interface IAllDoctorPresenter {
    void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);

    void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);
}
