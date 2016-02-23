package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.IBasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public interface IPatientPresenter extends IBasePresenter{
    void showPatientList(ArrayList<PatientBean> patientBeen);
    void appendPatientList(ArrayList<PatientBean> patientBeen);
}
