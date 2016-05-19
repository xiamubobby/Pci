package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.PatientEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/19.
 */
public interface PatientModelContract {
    interface Callback extends BaseModelListener{
        void onReceivePatientSuccess(PatientEntity entity);
    }

    interface Actions extends IBaseModel{
        void getPatientList(String doctorId,Callback callback);
    }
}
