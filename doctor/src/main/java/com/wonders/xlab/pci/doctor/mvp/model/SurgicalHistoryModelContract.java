package com.wonders.xlab.pci.doctor.mvp.model;

import com.wonders.xlab.pci.doctor.mvp.entity.SurgicalHistoryEntity;

import im.hua.library.base.mvp.IBaseModel;
import im.hua.library.base.mvp.listener.BaseModelListener;

/**
 * Created by hua on 16/5/5.
 */
public interface SurgicalHistoryModelContract {
    interface Callback extends BaseModelListener {
        void onReceiveSurgicalHistorySuccess(SurgicalHistoryEntity entity);
    }

    interface Actions extends IBaseModel {
        void getSurgicalHistory(String patientId, int pageIndex,Callback callback);
    }
}
