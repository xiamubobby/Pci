package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public interface PatientPresenterListener extends BasePresenterListener {
    void showPatientList(ArrayList<PatientBean> patientBeen);
    void appendPatientList(ArrayList<PatientBean> patientBeen);
}
