package com.wonders.xlab.pci.doctor.mvp.presenter.impl;

import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;
import com.wonders.xlab.pci.doctor.mvp.model.PatientModel;

import java.util.ArrayList;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public class PatientPresenter extends BasePresenter implements PatientModel.PatientModelListener {
    private PatientPresenterListener mIPatientPresenter;
    private PatientModel mPatientModel;

    private int mPageIndex = 0;
    private boolean mIsLast = false;

    public PatientPresenter(PatientPresenterListener IPatientPresenter) {
        mIPatientPresenter = IPatientPresenter;
        mPatientModel = new PatientModel(this);
        addModel(mPatientModel);
    }

    public void getPatientList(String doctorId) {
        mPatientModel.getPatientList(doctorId);
    }

    @Override
    public void onReceivePatientSuccess(PatientEntity entity) {
        ArrayList<PatientBean> patientBeen = new ArrayList<>();
        if (null == entity.getRet_values()) {
            mIPatientPresenter.showError("数据为空，请重试！");
            return;
        }
        for (int i = 0; i < entity.getRet_values().size(); i++) {
            PatientEntity.RetValuesEntity valuesEntity = entity.getRet_values().get(i);

            PatientBean bean = new PatientBean();
            bean.setPatientId(valuesEntity.getId());
            bean.setAge(valuesEntity.getAge());
            bean.setGender(valuesEntity.getGender());
            bean.setHistory(valuesEntity.getSymptom());
            bean.setPortrait(valuesEntity.getAvatarUrl());
            bean.setPatientName(valuesEntity.getName());
            bean.setTimeAfterSurgery(valuesEntity.getLastOperationTime());
            bean.setGroupName(valuesEntity.getGroupName());
            bean.setGroupId(valuesEntity.getDoctorGroupId());
            bean.setImGroupId(valuesEntity.getImGroupId());
            bean.setPhoneNumber(valuesEntity.getTel());

            patientBeen.add(bean);
        }

        mIPatientPresenter.showPatientList(patientBeen);

    }

    @Override
    public void onReceiveFailed(String message) {
        mIPatientPresenter.showError(message);
    }

    public interface PatientPresenterListener extends BasePresenterListener {
        void showPatientList(ArrayList<PatientBean> patientBeen);
        void appendPatientList(ArrayList<PatientBean> patientBeen);
    }
}
