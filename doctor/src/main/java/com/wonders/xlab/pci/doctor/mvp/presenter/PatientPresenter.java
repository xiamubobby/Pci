package com.wonders.xlab.pci.doctor.mvp.presenter;

import com.wonders.xlab.pci.doctor.module.patient.bean.PatientBean;
import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;
import com.wonders.xlab.pci.doctor.mvp.model.PatientModel;
import com.wonders.xlab.pci.doctor.mvp.model.impl.IPatientModel;
import com.wonders.xlab.pci.doctor.mvp.presenter.impl.IPatientPresenter;

import java.util.ArrayList;

import im.hua.library.base.mvp.BasePresenter;

/**
 * Created by hua on 16/2/22.
 */
public class PatientPresenter extends BasePresenter implements IPatientModel {
    private IPatientPresenter mIPatientPresenter;
    private PatientModel mPatientModel;

    private int mPageIndex = 0;
    private boolean mIsLast = false;

    public PatientPresenter(IPatientPresenter IPatientPresenter) {
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
            bean.setGroupId(valuesEntity.getGroupId());
            bean.setPhoneNumber(valuesEntity.getTel());

            patientBeen.add(bean);
        }

        mIPatientPresenter.showPatientList(patientBeen);

    }

    @Override
    public void onReceiveFailed(String message) {
        mIPatientPresenter.showError(message);
    }
}
