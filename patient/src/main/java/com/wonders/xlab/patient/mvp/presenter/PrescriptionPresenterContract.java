package com.wonders.xlab.patient.mvp.presenter;


import com.wonders.xlab.patient.module.healthrecord.prescription.adapter.bean.PrescriptionBean;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by jimmy on 16/5/5.
 */
public interface PrescriptionPresenterContract {

    interface ViewListener extends BasePagePresenterListener {
        void showPrescriptionList(List<PrescriptionBean> prescriptionBeanList);

        void appendPrescriptionList(List<PrescriptionBean> prescriptionBeanList);
    }

    interface Actions extends IBasePresenter {
        void getPrescriptionList(String patientId, boolean isRefresh);
    }
}
