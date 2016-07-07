package com.wonders.xlab.patient.mvp.presenter;

import com.wonders.xlab.patient.mvp.entity.DoctorListEntity;
import com.wonders.xlab.patient.mvp.model.DoctorListModel;
import com.wonders.xlab.patient.mvp.model.DoctorListModelContract;
import com.wonders.xlab.patient.mvp.presenter.DoctorListPresenterContract;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePresenter;

/**
 * Created by xiamubobby on 16/7/6.
 */

public class DoctorListPresenter extends BasePresenter implements DoctorListPresenterContract.Action {

    DoctorListModel model;
    DoctorListPresenterContract.ViewListener listener;

    @Inject
    public DoctorListPresenter(DoctorListPresenterContract.ViewListener listener, DoctorListModel model) {
        this.model = model;
        this.listener = listener;
    }

    @Override
    public void getDoctorList(String patientId) {
        model.getDoctorList(patientId, new DoctorListModelContract.Callback() {
            @Override
            public void onDoctorListReceiveSucceed(DoctorListEntity entity) {
                DoctorListEntity et = entity;
                listener.showDoctorList(entity.getRet_values().getDoctorPatientRelationDoctorList());
            }

            @Override
            public void onReceiveFailed(int code, String message) {
//                listener.hideLoading();
            }
        });
    }
}
