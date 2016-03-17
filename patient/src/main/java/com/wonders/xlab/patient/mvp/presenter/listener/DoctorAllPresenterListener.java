package com.wonders.xlab.patient.mvp.presenter.listener;

import com.wonders.xlab.patient.module.doctors.adapter.bean.AllDoctorItemBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/14.
 */
public interface DoctorAllPresenterListener extends BasePresenterListener{
    void showAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);

    void appendAllDoctorList(ArrayList<AllDoctorItemBean> myDoctorBeanList);
}
