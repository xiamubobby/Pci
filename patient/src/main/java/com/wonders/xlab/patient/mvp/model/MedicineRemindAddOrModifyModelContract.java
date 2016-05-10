package com.wonders.xlab.patient.mvp.model;

import com.wonders.xlab.patient.mvp.entity.request.MedicineRemindEditBody;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by WZH on 16/5/5.
 */
public interface MedicineRemindAddOrModifyModelContract {

    interface Callback extends BaseModelListener {

        void addOrModifySuccess(String message);
    }

    interface Actions extends IBaseModel {

        void addOrModify(String patientId, MedicineRemindEditBody body, Callback callback);
    }
}