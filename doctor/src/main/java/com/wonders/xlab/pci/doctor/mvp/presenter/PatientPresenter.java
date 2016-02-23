package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.Constant;
import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IPatientModel;
import com.wonders.xlab.pci.doctor.mvp.model.PatientModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IPatientPresenter;

import java.util.ArrayList;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public class PatientPresenter extends BasePresenter implements IPatientModel {
    private IPatientPresenter mIPatientPresenter;
    private PatientModel mPatientModel;

    public PatientPresenter(IPatientPresenter IPatientPresenter) {
        mIPatientPresenter = IPatientPresenter;
        mPatientModel = new PatientModel(this);
        addModel(mPatientModel);
    }

    public void getPatientList() {
        onReceivePatientSuccess(null);
//        mPatientModel.getPatientList();
    }

    @Override
    public void onReceivePatientSuccess(PatientEntity entity) {
        ArrayList<PatientBean> patientBeen = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PatientBean bean = new PatientBean();
            bean.setPatientId("" + i);
            bean.setAge("1" + i);
            bean.setGender("男");
            bean.setHistory("稳定性心绞痛");
            bean.setPortrait(Constant.DEFAULT_PORTRAIT);
            bean.setPatientName("张三");
            bean.setTimeAfterSurgery("术后六个月");
            bean.setPhoneNumber("13800138000");

            patientBeen.add(bean);
        }

        mIPatientPresenter.showPatientList(patientBeen);

    }

    @Override
    public void onReceiveFailed(String message) {

    }
}
