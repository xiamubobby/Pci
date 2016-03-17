package com.wonders.xlab.patient.mvp.presenter.listener;

import com.wonders.xlab.patient.module.doctors.adapter.bean.MyDoctorItemBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/14.
 */
public interface DoctorMyPresenterListener extends BasePresenterListener{
    void showMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);

    void appendMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);
}
