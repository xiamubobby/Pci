package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.entity.DoctorListEntity;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;

import java.util.List;

import im.hua.library.base.mvp.IBasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by xiamubobby on 16/7/6.
 */

public interface DoctorListPresenterContract {
    interface ViewListener extends BasePresenterListener {
        void showDoctorList(List<DoctorListEntity.DoctorPatientRelationDoctorList.DoctorPatientRelationDoctor> list);
    }
    interface Action extends IBasePresenter {
        void getDoctorList(String patientId);
    }
}
