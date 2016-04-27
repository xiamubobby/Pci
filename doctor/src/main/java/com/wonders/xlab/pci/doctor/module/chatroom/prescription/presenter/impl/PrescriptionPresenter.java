package com.wonders.xlab.pci.doctor.module.chatroom.prescription.presenter.impl;

import com.wonders.xlab.pci.doctor.module.chatroom.prescription.adapter.bean.PrescriptionBean;
import com.wonders.xlab.pci.doctor.module.chatroom.prescription.presenter.IPrescriptionPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePagePresenter;
import im.hua.library.base.mvp.listener.BasePagePresenterListener;

/**
 * Created by hua on 16/4/26.
 */
public class PrescriptionPresenter extends BasePagePresenter implements IPrescriptionPresenter {
    private PrescriptionPresenterListener mListener;

    public PrescriptionPresenter(PrescriptionPresenterListener listener) {
        mListener = listener;
    }

    @Override
    public void getPrescriptionList(String patientId, String doctorId) {
        List<PrescriptionBean> prescriptionBeanList = new ArrayList<>();
        List<String> medicineList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            medicineList.add("药品" + i);
        }
        for (int i = 0; i < 5; i++) {
            PrescriptionBean bean = new PrescriptionBean();
            bean.hospitalName.set("瑞金医院");
            bean.recordTimeInMill.set(Calendar.getInstance().getTimeInMillis());
            bean.medicineList.set(medicineList);

            prescriptionBeanList.add(bean);
        }
        mListener.showPrescriptionList(prescriptionBeanList);
    }

    public interface PrescriptionPresenterListener extends BasePagePresenterListener {
        void showPrescriptionList(List<PrescriptionBean> prescriptionBeanList);
    }
}
