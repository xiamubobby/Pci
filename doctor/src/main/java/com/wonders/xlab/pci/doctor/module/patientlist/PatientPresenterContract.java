package com.wonders.xlab.pci.doctor.module.patientlist;

import com.wonders.xlab.pci.doctor.module.patientlist.bean.PatientBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/5/19.
 */
public interface PatientPresenterContract {
    interface ViewListener extends BasePagePresenterListener{
        void showPatientList(ArrayList<PatientBean> patientBeen);

        void appendPatientList(ArrayList<PatientBean> patientBeen);
    }

    interface Actions extends IBasePresenter {
        void getPatientList(String doctorId);
    }

}
