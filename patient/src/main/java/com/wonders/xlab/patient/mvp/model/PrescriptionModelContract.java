package com.wonders.xlab.patient.mvp.model;


import com.wonders.xlab.patient.mvp.entity.PrescriptionEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by jimmy on 16/5/5.
 */
public interface PrescriptionModelContract {

    interface Callback extends BaseModelListener {
        void getPrescriptionListSuccess(PrescriptionEntity entity);
    }

    interface Actions extends IBaseModel {
        void getPrescriptionList(String patient, int pageIndex, Callback callback);
    }
}
