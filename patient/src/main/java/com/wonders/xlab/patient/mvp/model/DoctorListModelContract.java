package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.DoctorListEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by xiamubobby on 16/7/6.
 */

public interface DoctorListModelContract {
    interface Callback extends BaseModelListener {
        void onDoctorListReceiveSucceed(DoctorListEntity entity);
    }

    interface Action extends IBaseModel {
        void getDoctorList(String patientId, final DoctorListModelContract.Callback callback);
    }

}
