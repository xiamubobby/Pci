package com.wonders.xlab.pci.doctor.module.patient.view;

import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.BaseView;

/**
 * Created by hua on 16/2/19.
 */
public interface PatientView extends BaseView {
    void showPatientList(ArrayList<PatientBean> patientBeen);
    void appendPatientList(ArrayList<PatientBean> patientBeen);
}
