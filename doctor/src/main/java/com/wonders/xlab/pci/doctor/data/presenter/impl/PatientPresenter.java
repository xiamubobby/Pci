package com.wonders.xlab.pci.doctor.data.presenter.impl;

import com.wonders.xlab.pci.doctor.data.entity.PatientEntity;
import com.wonders.xlab.pci.doctor.data.model.impl.PatientModel;
import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;

import java.util.ArrayList;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/2/22.
 */
public class PatientPresenter extends BasePresenter implements PatientModel.PatientModelListener {
    private PatientPresenterListener mListener;
    private PatientModel mPatientModel;

    private int mPageIndex = 0;
    private boolean mIsLast = false;

    public PatientPresenter(PatientPresenterListener Listener) {
        mListener = Listener;
        mPatientModel = new PatientModel(this);
        addModel(mPatientModel);
    }

    public void getPatientList(String doctorId) {
        mListener.showLoading("");
        mPatientModel.getPatientList(doctorId);
    }

    @Override
    public void onReceivePatientSuccess(PatientEntity entity) {
        mListener.hideLoading();
        ArrayList<PatientBean> patientBeen = new ArrayList<>();
        if (null == entity.getRet_values()) {
            mListener.showEmptyView(entity.getMessage());
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
            bean.setOwnerId(valuesEntity.getOwnerId());
            bean.setImGroupId(valuesEntity.getImGroupId());
            bean.setPhoneNumber(valuesEntity.getTel());

            patientBeen.add(bean);
        }
        mListener.showPatientList(patientBeen);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mListener, code, message);
    }

    public interface PatientPresenterListener extends BasePresenterListener {
        void showPatientList(ArrayList<PatientBean> patientBeen);

        void appendPatientList(ArrayList<PatientBean> patientBeen);
    }
}
