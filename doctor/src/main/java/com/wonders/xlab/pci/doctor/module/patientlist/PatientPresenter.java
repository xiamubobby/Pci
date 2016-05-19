package com.wonders.xlab.pci.doctor.module.patientlist;

import com.wonders.xlab.pci.doctor.module.patientlist.bean.PatientBean;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;
import com.wonders.xlab.pci.doctor.mvp.model.PatientModel;
import com.wonders.xlab.pci.doctor.mvp.model.PatientModelContract;

import java.util.ArrayList;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public class PatientPresenter extends BasePresenter implements PatientPresenterContract.Actions,PatientModelContract.Callback {
    private PatientPresenterContract.ViewListener mListener;
    private PatientModelContract.Actions mPatientModel;

    private int mPageIndex = 0;
    private boolean mIsLast = false;

    @Inject
    public PatientPresenter(PatientPresenterContract.ViewListener Listener,PatientModel patientModel) {
        mListener = Listener;
        mPatientModel = patientModel;
        addModel(mPatientModel);
    }

    @Override
    public void getPatientList(String doctorId) {
        mListener.showLoading("");
        mPatientModel.getPatientList(doctorId,this);
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
}
